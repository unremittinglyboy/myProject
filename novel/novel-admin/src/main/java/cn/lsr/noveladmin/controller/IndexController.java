package cn.lsr.noveladmin.controller;

import cn.lsr.noveladmin.RequestParam.BookParam;
import cn.lsr.noveladmin.Service.*;
import cn.lsr.noveladmin.constants.mqConstants;
import cn.lsr.noveladmin.helpPo.serie;
import cn.lsr.noveladmin.model.*;
import cn.lsr.noveladmin.util.FileUtils;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/admin")
public class IndexController {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private GraphService graphService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${web.upload-path}")
    private String path;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        Integer i;
        //vip总数
        i = userService.getAllVips();
        int countVips = i.intValue();
        //作家总数
        i = authorService.GetAllAuthorsCount();
        int countAuthors = i.intValue();
        //书的总数
        i = bookService.getAllBooksCount();
        int countBooks = i.intValue();

        //图标
        //将当前时间下的图表数据更新到存放图标数据的数据表中。
        Graphtimecount g = new Graphtimecount((long) countAuthors, (long) countVips, (long) countBooks);
        graphService.insertGraph(g);

        //设计图表时间下标数组
        LocalDate current = LocalDate.now();
        int curYear = current.getYear();
        LinkedList<String> showYears = new LinkedList<>();
        for (int x = 0; x < 6; x++) {
            showYears.addFirst(String.valueOf(curYear - x));
        }

        //设计图表时间下标对应统计值
        LinkedList<String> series = new LinkedList<>();
        designSeries(series, curYear);

        //作家管理
        PageInfo<Author> allAuthors = authorService.getAllAuthors(1, 5);

        //类型管理
        PageInfo<Type> allTypes = typeService.getAllTypes(1, 5);

        //标签管理
        PageInfo<Tag> allTags = tagService.getAllTags(1, 5);

        //用户管理
        PageInfo<User> allUsers = userService.getAllUsers(1, 5);

        //管理员管理
        PageInfo<Administrator> allAdministrators = administratorService.getAllAdministrators(1, 3);

        //小说管理
        PageInfo<Book> allBooks = bookService.getFuzzyQueryAllBooks(1, 5, null);

        //当前登录的用户名
        String nowAdministrator = (String) SecurityUtils.getSubject().getPrincipal();

        mv.addObject("countAuthors", countAuthors);
        mv.addObject("countVips", countVips);
        mv.addObject("countBooks", countBooks);
        mv.addObject("showYears", JSON.toJSONString(showYears));
        mv.addObject("series", series);
        mv.addObject("allAuthors", allAuthors);
        mv.addObject("allUsers", allUsers);
        mv.addObject("allTypes", allTypes);
        mv.addObject("allTags", allTags);
        mv.addObject("allAdministrators", allAdministrators);
        mv.addObject("allBooks", allBooks);
        mv.addObject("nowAdminName", nowAdministrator);
        mv.setViewName("index");
        return mv;
    }

    //Book
    @PostMapping("/index/nextBookSearchPage")
    public String nextBookSearchPage(@ModelAttribute BookParam bookParam, @ModelAttribute PageInfo<Book> info, Model model) {
        int page = info.getPageNum();
        int pageSize = info.getPageSize();
        int pages = info.getPages();
        page++;
        if (page > pages) {
            page = pages;
        }
        updateAllBooksInModel(bookParam, model, page, pageSize);
        return "index::bookSearchedContents";
    }

    @PostMapping("/index/preBookSearchPage")
    public String preBookSearchPage(@ModelAttribute BookParam bookParam, @ModelAttribute PageInfo<Book> info, Model model) {
        int page = info.getPageNum();
        int pageSize = info.getPageSize();
        page--;
        if (page < 1) {
            page = 1;
        }
        updateAllBooksInModel(bookParam, model, page, pageSize);
        return "index::bookSearchedContents";
    }

    @PostMapping("/index/skipBookSearchPage")
    public String skipBookSearchPage(@ModelAttribute BookParam bookParam, @ModelAttribute PageInfo<Book> info, Model model) {
        int page = info.getPageNum();
        int pageSize = info.getPageSize();
        updateAllBooksInModel(bookParam, model, page, pageSize);
        return "index::bookSearchedContents";
    }

    @PostMapping("/index/bookSearch")
    public String search(@ModelAttribute BookParam bookParam, @ModelAttribute PageInfo<Book> info, Model model) {
        int page = info.getPageNum();
        int pageSize = info.getPageSize();
        updateAllBooksInModel(bookParam, model, page, pageSize);
        return "index::bookSearchedContents";
    }

    @PostMapping("/index/insertBook")
    public String insertBook(
            @ModelAttribute PageInfo<Book> allBooks,
            Model model,
            @RequestParam("picUrlFile") MultipartFile file,
            @Validated Book book, Errors errors) throws RuntimeException {
        int page = allBooks.getPageNum();
        int pageSize = allBooks.getPageSize();
        int i = -1;
        //图片上传
        if (!errors.hasErrors()) {
            List<String> ansNameAndRealPath = FileUtils.upload(path, file.getOriginalFilename());
            String ansName = ansNameAndRealPath.get(0);
            String realPath = ansNameAndRealPath.get(1);
            if (!ansName.equals("")) {
                book.setPicUrl("/images/" + ansName);
            }
            i = bookService.insertBook(book, rabbitTemplate);
            if(!realPath.equals(path + "/")) FileUtils.genPic(file, realPath);
        } else {
            List<String> bookInsertErrors = new ArrayList<>();
            List<ObjectError> allErrors = errors.getAllErrors();
            for (ObjectError error : allErrors) {
                bookInsertErrors.add(error.getDefaultMessage());
            }
            if (bookInsertErrors != null && bookInsertErrors.size() != 0) {
                model.addAttribute("bookInsertErrors", bookInsertErrors);
            }
            allBooks = bookService.getFuzzyQueryAllBooks(1, 5, null);
        }
        PageInfo<Type> allTypes = typeService.getAllTypes(1, 5);
        PageInfo<Author> allAuthors = authorService.getAllAuthors(1, 5);
        model.addAttribute("allTypes", allTypes);
        model.addAttribute("allBooks", allBooks);
        model.addAttribute("allAuthors", allAuthors);
        return "index::bookInsertForm";
    }


    //Author
    @PostMapping("/index/nextAuthorPage")
    public String nextAuthorsPage(@ModelAttribute PageInfo<Author> allAuthors, Model model, @RequestParam String authorsSearch) {
        int page = allAuthors.getPageNum();
        int pageSize = allAuthors.getPageSize();
        int pages = allAuthors.getPages();
        page++;
        if (page > pages) {
            page = pages;
        }
        getAuthorPageIncludeSearch(authorsSearch, model, page, pageSize);
        return "index::authorsTable";
    }

    @PostMapping("/index/preAuthorPage")
    public String preAuthorsPage(@ModelAttribute PageInfo<Author> allAuthors, Model model, @RequestParam String authorsSearch) {
        int page = allAuthors.getPageNum();
        int pageSize = allAuthors.getPageSize();
        page--;
        if (page < 1) {
            page = 1;
        }
        getAuthorPageIncludeSearch(authorsSearch, model, page, pageSize);
        return "index::authorsTable";
    }

    @PostMapping("/index/authorSearch")
    public String searchAuthors(@ModelAttribute PageInfo<Author> allAuthors, @RequestParam String authorsSearch, Model model) {
        int page = allAuthors.getPageNum();
        int pageSize = allAuthors.getPageSize();
        getAuthorPageIncludeSearch(authorsSearch, model, page, pageSize);
        return "index::authorsTable";
    }

    @PostMapping("/index/skipAuthorPage")
    public String skipPageAuthors(@ModelAttribute PageInfo<Author> allAuthors, @RequestParam String authorsSearch, Model model) {
        int page = allAuthors.getPageNum();
        int pageSize = allAuthors.getPageSize();
        getAuthorPageIncludeSearch(authorsSearch, model, page, pageSize);
        return "index::authorsTable";
    }


    @PostMapping("/index/deleteAuthor")
    public String deleteAuthor(@ModelAttribute PageInfo<Author> allAuthors, @RequestParam Long id, @RequestParam String authorsSearch, Model model) {
        int page = allAuthors.getPageNum();
        int pageSize = allAuthors.getPageSize();
        Author deleteOne = authorService.getOne(id);
        if (deleteOne != null) authorService.deleteAuthor(id);
        getAuthorPageIncludeSearch(authorsSearch, model, page, pageSize);
        return "index::authorsTable";
    }


    @PostMapping("/index/insertAuthor")
    public String insertAuthor(@ModelAttribute PageInfo<Author> allAuthors,
                               @RequestParam String authorsSearch, Model model,
                               @ModelAttribute @Validated Author author, Errors errors) throws RuntimeException {
        int page = allAuthors.getPageNum();
        int pageSize = allAuthors.getPageSize();
        List<String> authorInsertErrors = new LinkedList<>();
        int i = -1;
        if (!errors.hasErrors()) {
            if (authorService.getAllPenName().contains(author.getPenName())) authorInsertErrors.add("笔名已存在");
            else {
                i = authorService.insertAuthor(author);
                model.addAttribute("insertAuthorSuccess", true);
            }
        }
        if (errors.hasErrors()) {
            List<ObjectError> allErrors = errors.getAllErrors();
            for (ObjectError error : allErrors) {
                authorInsertErrors.add(error.getDefaultMessage());
            }
        }
        if (authorInsertErrors != null && authorInsertErrors.size() != 0)
            model.addAttribute("authorInsertErrors", authorInsertErrors);
        if (i <= 0) model.addAttribute("insertAuthorFault", true);
        getAuthorPageIncludeSearch(authorsSearch, model, page, pageSize);
        return "index::authorsTemplate";
    }


    @PostMapping("/index/updateAuthor")
    public String updatetAuthor(@ModelAttribute PageInfo<Author> allAuthors,
                                @RequestParam String authorsSearch, Model model,
                                @ModelAttribute @Validated Author author, Errors errors) throws RuntimeException {
        int page = allAuthors.getPageNum();
        int pageSize = allAuthors.getPageSize();
        List<String> authorEditErrors = new LinkedList<>();
        author.setCreateTime(authorService.getOne(author.getId()).getCreateTime());
        int i = -1;
        if (!errors.hasErrors()) {
            if (authorService.getAllPenNameNotMe(author.getPenName()).contains(author.getPenName()))
                authorEditErrors.add("笔名已存在");
            else {
                i = authorService.updateAuthor(author);
                model.addAttribute("editAuthorSuccess", true);
            }
        }
        if (errors.hasErrors()) {
            List<ObjectError> allErrors = errors.getAllErrors();
            for (ObjectError error : allErrors) {
                authorEditErrors.add(error.getDefaultMessage());
            }
        }
        if (authorEditErrors != null && authorEditErrors.size() != 0)
            model.addAttribute("authorEditErrors", authorEditErrors);
        if (i <= 0) model.addAttribute("editAuthorFault", true);
        getAuthorPageIncludeSearch(authorsSearch, model, page, pageSize);
        return "index::authorsTemplate";
    }

    //Tag
    @PostMapping("/index/nextTagPage")
    public String nextTagsPage(@ModelAttribute PageInfo<Tag> allTags, Model model, @RequestParam String tagsSearch) {
        int page = allTags.getPageNum();
        int pageSize = allTags.getPageSize();
        int pages = allTags.getPages();
        page++;
        if (page > pages) {
            page = pages;
        }
        getTagPageIncludeSearch(tagsSearch, model, page, pageSize);
        return "index::tagsTable";
    }

    @PostMapping("/index/preTagPage")
    public String preTagsPage(@ModelAttribute PageInfo<Tag> allTags, Model model, @RequestParam String tagsSearch) {
        int page = allTags.getPageNum();
        int pageSize = allTags.getPageSize();
        page--;
        if (page < 1) {
            page = 1;
        }
        getTagPageIncludeSearch(tagsSearch, model, page, pageSize);
        return "index::tagsTable";
    }

    @PostMapping("/index/tagSearch")
    public String searchTags(@ModelAttribute PageInfo<Tag> allTags, @RequestParam String tagsSearch, Model model) {
        int page = allTags.getPageNum();
        int pageSize = allTags.getPageSize();
        getTagPageIncludeSearch(tagsSearch, model, page, pageSize);
        return "index::tagsTable";
    }

    @PostMapping("/index/skipTagPage")
    public String skipPageTags(@ModelAttribute PageInfo<Tag> allTags, @RequestParam String tagsSearch, Model model) {
        int page = allTags.getPageNum();
        int pageSize = allTags.getPageSize();
        getTagPageIncludeSearch(tagsSearch, model, page, pageSize);
        return "index::tagsTable";
    }


    @PostMapping("/index/deleteTag")
    public String deleteTag(@ModelAttribute PageInfo<Tag> allTags, @RequestParam Integer id, @RequestParam String tagsSearch, Model model) {
        int page = allTags.getPageNum();
        int pageSize = allTags.getPageSize();
        Tag deleteOne = tagService.getOne(id);
        if (deleteOne != null) tagService.deleteTag(id);
        getTagPageIncludeSearch(tagsSearch, model, page, pageSize);
        return "index::tagsTable";
    }


    @PostMapping("/index/insertTag")
    public String insertTag(@ModelAttribute PageInfo<Tag> allTags,
                            @RequestParam String tagsSearch, Model model,
                            @ModelAttribute @Validated Tag tag, Errors errors) throws RuntimeException {
        int page = allTags.getPageNum();
        int pageSize = allTags.getPageSize();
        List<String> tagInsertErrors = new LinkedList<>();
        int i = -1;
        if (!errors.hasErrors()) {
            if (tagService.getAllTagName().contains(tag.getTagName())) tagInsertErrors.add("标签名已存在");
            else {
                i = tagService.insertTag(tag);
                model.addAttribute("insertTagSuccess", true);
            }
        }
        if (errors.hasErrors()) {
            List<ObjectError> allErrors = errors.getAllErrors();
            for (ObjectError error : allErrors) {
                tagInsertErrors.add(error.getDefaultMessage());
            }
        }
        if (tagInsertErrors != null && tagInsertErrors.size() != 0)
            model.addAttribute("tagInsertErrors", tagInsertErrors);
        if (i <= 0) model.addAttribute("insertTagFault", true);
        getTagPageIncludeSearch(tagsSearch, model, page, pageSize);
        return "index::tagsTemplate";
    }


    @PostMapping("/index/updateTag")
    public String updatetTag(@ModelAttribute PageInfo<Tag> allTags,
                             @RequestParam String tagsSearch, Model model,
                             @ModelAttribute @Validated Tag tag, Errors errors) throws RuntimeException {
        int page = allTags.getPageNum();
        int pageSize = allTags.getPageSize();
        tag.setCreateTime(tagService.getOne(tag.getId()).getCreateTime());
        List<String> tagEditErrors = new LinkedList<>();
        int i = -1;
        if (!errors.hasErrors()) {
            if (tagService.getAllTagName().contains(tag.getTagName())) tagEditErrors.add("标签名已存在");
            else {
                i = tagService.updateTag(tag);
                model.addAttribute("editTagSuccess", true);
            }
        }
        if (errors.hasErrors()) {
            List<ObjectError> allErrors = errors.getAllErrors();
            for (ObjectError error : allErrors) {
                tagEditErrors.add(error.getDefaultMessage());
            }
        }
        if (tagEditErrors != null && tagEditErrors.size() != 0) model.addAttribute("tagEditErrors", tagEditErrors);
        if (i <= 0) model.addAttribute("editTagFault", true);
        getTagPageIncludeSearch(tagsSearch, model, page, pageSize);
        return "index::tagsTemplate";
    }

    //Type
    @PostMapping("/index/nextTypePage")
    public String nextTypesPage(@ModelAttribute PageInfo<Type> allTypes, Model model, @RequestParam String typesSearch) {
        int page = allTypes.getPageNum();
        int pageSize = allTypes.getPageSize();
        int pages = allTypes.getPages();
        page++;
        if (page > pages) {
            page = pages;
        }
        getTypePageIncludeSearch(typesSearch, model, page, pageSize);
        return "index::typesTable";
    }

    @PostMapping("/index/preTypePage")
    public String preTypesPage(@ModelAttribute PageInfo<Type> allTypes, Model model, @RequestParam String typesSearch) {
        int page = allTypes.getPageNum();
        int pageSize = allTypes.getPageSize();
        page--;
        if (page < 1) {
            page = 1;
        }
        getTypePageIncludeSearch(typesSearch, model, page, pageSize);
        return "index::typesTable";
    }

    @PostMapping("/index/typeSearch")
    public String searchTypes(@ModelAttribute PageInfo<Type> allTypes, @RequestParam String typesSearch, Model model) {
        int page = allTypes.getPageNum();
        int pageSize = allTypes.getPageSize();
        getTypePageIncludeSearch(typesSearch, model, page, pageSize);
        return "index::typesTable";
    }

    @PostMapping("/index/skipTypePage")
    public String skipPageTypes(@ModelAttribute PageInfo<Type> allTypes, @RequestParam String typesSearch, Model model) {
        int page = allTypes.getPageNum();
        int pageSize = allTypes.getPageSize();
        getTypePageIncludeSearch(typesSearch, model, page, pageSize);
        return "index::typesTable";
    }


    @PostMapping("/index/deleteType")
    public String deleteType(@ModelAttribute PageInfo<Type> allTypes, @RequestParam Integer id, @RequestParam String typesSearch, Model model) {
        int page = allTypes.getPageNum();
        int pageSize = allTypes.getPageSize();
        Type deleteOne = typeService.getOne(id);
        if (deleteOne != null) typeService.deleteType(id);
        getTypePageIncludeSearch(typesSearch, model, page, pageSize);
        return "index::typesTable";
    }


    @PostMapping("/index/insertType")
    public String insertType(@ModelAttribute PageInfo<Type> allTypes,
                             @RequestParam String typesSearch, Model model,
                             @ModelAttribute @Validated Type type, Errors errors) throws RuntimeException {
        int page = allTypes.getPageNum();
        int pageSize = allTypes.getPageSize();
        List<String> typeInsertErrors = new LinkedList<>();
        int i = -1;
        if (!errors.hasErrors()) {
            if (typeService.getAllTypeName().contains(type.getTypeName())) typeInsertErrors.add("类型名已存在");
            else {
                i = typeService.insertType(type);
                model.addAttribute("insertTypeSuccess", true);
            }
        }
        if (errors.hasErrors()) {
            List<ObjectError> allErrors = errors.getAllErrors();
            for (ObjectError error : allErrors) {
                typeInsertErrors.add(error.getDefaultMessage());
            }
        }
        if (typeInsertErrors != null && typeInsertErrors.size() != 0)
            model.addAttribute("typeInsertErrors", typeInsertErrors);
        if (i <= 0) model.addAttribute("insertTypeFault", true);
        getTypePageIncludeSearch(typesSearch, model, page, pageSize);
        return "index::typesTemplate";
    }


    @PostMapping("/index/updateType")
    public String updatetType(@ModelAttribute PageInfo<Type> allTypes,
                              @RequestParam String typesSearch, Model model,
                              @ModelAttribute @Validated Type type, Errors errors) throws RuntimeException {
        int page = allTypes.getPageNum();
        int pageSize = allTypes.getPageSize();
        List<String> typeEditErrors = new LinkedList<>();
        type.setCreateTime(typeService.getOne(type.getId()).getCreateTime());
        int i = -1;
        if (!errors.hasErrors()) {
            if (typeService.getAllTypeName().contains(type.getTypeName())) typeEditErrors.add("类型名已存在");
            else {
                i = typeService.updateType(type);
                model.addAttribute("editTypeSuccess", true);
            }
        }
        if (errors.hasErrors()) {
            List<ObjectError> allErrors = errors.getAllErrors();
            for (ObjectError error : allErrors) {
                typeEditErrors.add(error.getDefaultMessage());
            }
        }
        if (typeEditErrors != null && typeEditErrors.size() != 0) model.addAttribute("typeEditErrors", typeEditErrors);
        if (i <= 0) model.addAttribute("editTypeFault", true);
        getTypePageIncludeSearch(typesSearch, model, page, pageSize);
        return "index::typesTemplate";
    }

    //Administrator
    @PostMapping("/index/nextAdministratorPage")
    public String nextAdministratorsPage(@ModelAttribute PageInfo<Administrator> allAdministrators, Model model, @RequestParam String administratorsSearch) {
        int page = allAdministrators.getPageNum();
        int pageSize = allAdministrators.getPageSize();
        int pages = allAdministrators.getPages();
        page++;
        if (page > pages) {
            page = pages;
        }
        getAdministratorPageIncludeSearch(administratorsSearch, model, page, pageSize);
        return "index::administratorsTable";
    }

    @PostMapping("/index/preAdministratorPage")
    public String preAdministratorsPage(@ModelAttribute PageInfo<Administrator> allAdministrators, Model model, @RequestParam String administratorsSearch) {
        int page = allAdministrators.getPageNum();
        int pageSize = allAdministrators.getPageSize();
        page--;
        if (page < 1) {
            page = 1;
        }
        getAdministratorPageIncludeSearch(administratorsSearch, model, page, pageSize);
        return "index::administratorsTable";
    }

    @PostMapping("/index/administratorSearch")
    public String searchAdministrators(@ModelAttribute PageInfo<Administrator> allAdministrators, @RequestParam String administratorsSearch, Model model) {
        int page = allAdministrators.getPageNum();
        int pageSize = allAdministrators.getPageSize();
        getAdministratorPageIncludeSearch(administratorsSearch, model, page, pageSize);
        return "index::administratorsTable";
    }

    @PostMapping("/index/skipAdministratorPage")
    public String skipPageAdministrators(@ModelAttribute PageInfo<Administrator> allAdministrators, @RequestParam String administratorsSearch, Model model) {
        int page = allAdministrators.getPageNum();
        int pageSize = allAdministrators.getPageSize();
        getAdministratorPageIncludeSearch(administratorsSearch, model, page, pageSize);
        return "index::administratorsTable";
    }


    @PostMapping("/index/deleteAdministrator")
    public String deleteAdministrator(@ModelAttribute PageInfo<Administrator> allAdministrators, @RequestParam Long id, @RequestParam String administratorsSearch, Model model) {
        int page = allAdministrators.getPageNum();
        int pageSize = allAdministrators.getPageSize();
        Administrator deleteOne = administratorService.getOne(id);
        if (deleteOne != null) administratorService.deleteAdministrator(id);
        getAdministratorPageIncludeSearch(administratorsSearch, model, page, pageSize);
        return "index::administratorsTable";
    }


    @PostMapping("/index/insertAdministrator")
    public String insertAdministrator(@ModelAttribute PageInfo<Administrator> allAdministrators,
                                      @RequestParam String administratorsSearch, Model model,
                                      @ModelAttribute @Validated Administrator administrator, Errors errors) throws RuntimeException {
        int page = allAdministrators.getPageNum();
        int pageSize = allAdministrators.getPageSize();
        List<String> administratorInsertErrors = new LinkedList<>();
        int i = -1;
        if (!errors.hasErrors()) {
            if (administratorService.getAllAdminName().contains(administrator.getAdminName()))
                administratorInsertErrors.add("管理名已存在");
            else {
                i = administratorService.insertAdministrator(administrator);
                model.addAttribute("insertAdministratorSuccess", true);
            }
        }
        if (errors.hasErrors()) {
            List<ObjectError> allErrors = errors.getAllErrors();
            for (ObjectError error : allErrors) {
                administratorInsertErrors.add(error.getDefaultMessage());
            }
        }
        if (administratorInsertErrors != null && administratorInsertErrors.size() != 0)
            model.addAttribute("administratorInsertErrors", administratorInsertErrors);
        if (i <= 0) model.addAttribute("insertAdministratorFault", true);
        getAdministratorPageIncludeSearch(administratorsSearch, model, page, pageSize);
        return "index::administratorsTemplate";
    }


    @PostMapping("/index/updateAdministrator")
    public String updatetAdministrator(@ModelAttribute PageInfo<Administrator> allAdministrators,
                                       @RequestParam String administratorsSearch, Model model,
                                       @ModelAttribute @Validated Administrator administrator, Errors errors) throws RuntimeException {
        int page = allAdministrators.getPageNum();
        int pageSize = allAdministrators.getPageSize();
        List<String> administratorEditErrors = new LinkedList<>();
        administrator.setCreateTime(administratorService.getOne(administrator.getId()).getCreateTime());
        int i = -1;
        if (!errors.hasErrors()) {
            if (administratorService.getAllAdminNameNotMe(administrator.getAdminName()).contains(administrator.getAdminName()))
                administratorEditErrors.add("管理名已存在");
            else {
                i = administratorService.updateAdministrator(administrator);
                model.addAttribute("editAdministratorSuccess", true);
            }
        }
        if (errors.hasErrors()) {
            List<ObjectError> allErrors = errors.getAllErrors();
            for (ObjectError error : allErrors) {
                administratorEditErrors.add(error.getDefaultMessage());
            }
        }
        if (administratorEditErrors != null && administratorEditErrors.size() != 0)
            model.addAttribute("administratorEditErrors", administratorEditErrors);
        if (i <= 0) model.addAttribute("editAdministratorFault", true);
        getAdministratorPageIncludeSearch(administratorsSearch, model, page, pageSize);
        return "index::administratorsTemplate";
    }

    //User
    @PostMapping("/index/nextUserPage")
    public String nextUsersPage(@ModelAttribute PageInfo<User> allUsers, Model model, @RequestParam String usersSearch) {
        int page = allUsers.getPageNum();
        int pageSize = allUsers.getPageSize();
        int pages = allUsers.getPages();
        page++;
        if (page > pages) {
            page = pages;
        }
        getUserPageIncludeSearch(usersSearch, model, page, pageSize);
        return "index::usersTable";
    }

    @PostMapping("/index/preUserPage")
    public String preUsersPage(@ModelAttribute PageInfo<User> allUsers, Model model, @RequestParam String usersSearch) {
        int page = allUsers.getPageNum();
        int pageSize = allUsers.getPageSize();
        page--;
        if (page < 1) {
            page = 1;
        }
        getUserPageIncludeSearch(usersSearch, model, page, pageSize);
        return "index::usersTable";
    }

    @PostMapping("/index/userSearch")
    public String searchUsers(@ModelAttribute PageInfo<User> allUsers, @RequestParam String usersSearch, Model model) {
        int page = allUsers.getPageNum();
        int pageSize = allUsers.getPageSize();
        getUserPageIncludeSearch(usersSearch, model, page, pageSize);
        return "index::usersTable";
    }

    @PostMapping("/index/skipUserPage")
    public String skipPageUsers(@ModelAttribute PageInfo<User> allUsers, @RequestParam String usersSearch, Model model) {
        int page = allUsers.getPageNum();
        int pageSize = allUsers.getPageSize();
        getUserPageIncludeSearch(usersSearch, model, page, pageSize);
        return "index::usersTable";
    }


    @PostMapping("/index/deleteUser")
    public String deleteUser(@ModelAttribute PageInfo<User> allUsers, @RequestParam Long id, @RequestParam String usersSearch, Model model) {
        int page = allUsers.getPageNum();
        int pageSize = allUsers.getPageSize();
        User deleteOne = userService.getOne(id);
        if (deleteOne != null) userService.deleteUser(id);
        getUserPageIncludeSearch(usersSearch, model, page, pageSize);
        return "index::usersTable";
    }


    @PostMapping("/index/insertUser")
    public String insertUser(@ModelAttribute PageInfo<User> allUsers,
                             @RequestParam String usersSearch, Model model,
                             @ModelAttribute @Validated User user, Errors errors) throws RuntimeException {
        int page = allUsers.getPageNum();
        int pageSize = allUsers.getPageSize();
        List<String> userInsertErrors = new LinkedList<>();
        int i = -1;
        if (!errors.hasErrors()) {
            if (userService.getAllUserName().contains(user.getUserName())) userInsertErrors.add("用户名已存在");
            else {
                i = userService.insertUser(user);
                model.addAttribute("insertUserSuccess", true);
            }
        }
        if (errors.hasErrors()) {
            List<ObjectError> allErrors = errors.getAllErrors();
            for (ObjectError error : allErrors) {
                userInsertErrors.add(error.getDefaultMessage());
            }
        }
        if (userInsertErrors != null && userInsertErrors.size() != 0)
            model.addAttribute("userInsertErrors", userInsertErrors);
        if (i <= 0) model.addAttribute("insertUserFault", true);
        getUserPageIncludeSearch(usersSearch, model, page, pageSize);
        return "index::usersTemplate";
    }


    @PostMapping("/index/updateUser")
    public String updatetUser(@ModelAttribute PageInfo<User> allUsers,
                              @RequestParam String usersSearch, Model model,
                              @ModelAttribute @Validated User user, Errors errors) throws RuntimeException {
        int page = allUsers.getPageNum();
        int pageSize = allUsers.getPageSize();
        List<String> userEditErrors = new LinkedList<>();
        user.setCreateTime(userService.getOne(user.getId()).getCreateTime());
        int i = -1;
        if (!errors.hasErrors()) {
            if (userService.getAllUserNameNotMe(user.getUserName()).contains(user.getUserName()))
                userEditErrors.add("用户名已存在");
            else {
                i = userService.updateUser(user);
                model.addAttribute("editUserSuccess", true);
            }
        }
        if (errors.hasErrors()) {
            List<ObjectError> allErrors = errors.getAllErrors();
            for (ObjectError error : allErrors) {
                userEditErrors.add(error.getDefaultMessage());
            }
        }
        if (userEditErrors != null && userEditErrors.size() != 0)
            model.addAttribute("userEditErrors", userEditErrors);
        if (i <= 0) model.addAttribute("editUserFault", true);
        getUserPageIncludeSearch(usersSearch, model, page, pageSize);
        return "index::usersTemplate";
    }


    //减少Handler代码长度的各模块提取函数
    private void getUserPageIncludeSearch(@RequestParam String usersSearch, Model model, int page, int pageSize) {
        PageInfo<User> allUsers;
        if (!usersSearch.equals("") && usersSearch.length() != 0) {
            allUsers = userService.getFuzzyQueryAllUsers(page, pageSize, usersSearch);
            model.addAttribute("allUsers", allUsers);
        } else {
            allUsers = userService.getAllUsers(page, pageSize);
            model.addAttribute("allUsers", allUsers);
        }
    }

    private void getTypePageIncludeSearch(@RequestParam String typesSearch, Model model, int page, int pageSize) {
        PageInfo<Type> allTypes;
        if (!typesSearch.equals("") && typesSearch.length() != 0) {
            allTypes = typeService.getFuzzyQueryAllTypes(page, pageSize, typesSearch);
            model.addAttribute("allTypes", allTypes);
        } else {
            allTypes = typeService.getAllTypes(page, pageSize);
            model.addAttribute("allTypes", allTypes);
        }
    }

    private void getAuthorPageIncludeSearch(@RequestParam String authorsSearch, Model model, int page, int pageSize) {
        PageInfo<Author> allAuthors;
        if (!authorsSearch.equals("") && authorsSearch.length() != 0) {
            allAuthors = authorService.getFuzzyQueryAllAuthors(page, pageSize, authorsSearch);
            model.addAttribute("allAuthors", allAuthors);
        } else {
            allAuthors = authorService.getAllAuthors(page, pageSize);
            model.addAttribute("allAuthors", allAuthors);
        }
    }

    private void getTagPageIncludeSearch(@RequestParam String tagsSearch, Model model, int page, int pageSize) {
        PageInfo<Tag> allTags;
        if (!tagsSearch.equals("") && tagsSearch.length() != 0) {
            allTags = tagService.getFuzzyQueryAllTags(page, pageSize, tagsSearch);
            model.addAttribute("allTags", allTags);
        } else {
            allTags = tagService.getAllTags(page, pageSize);
            model.addAttribute("allTags", allTags);
        }
    }

    private void getAdministratorPageIncludeSearch(@RequestParam String administratorsSearch, Model model, int page, int pageSize) {
        PageInfo<Administrator> allAdministrators;
        if (!administratorsSearch.equals("") && administratorsSearch.length() != 0) {
            allAdministrators = administratorService.getFuzzyQueryAllAdministrators(page, pageSize, administratorsSearch);
            model.addAttribute("allAdministrators", allAdministrators);
        } else {
            allAdministrators = administratorService.getAllAdministrators(page, pageSize);
            model.addAttribute("allAdministrators", allAdministrators);
        }
    }

    //减少Handler代码长度的提取函数
    private void designSeries(LinkedList<String> series, int curYear) {
        LinkedList<Integer> data1 = new LinkedList<>();
        LinkedList<Integer> data2 = new LinkedList<>();
        LinkedList<Integer> data3 = new LinkedList<>();
        for (int z = 0; z < 6; z++) {
            Graphtimecount ans = graphService.selectByYear(curYear - z);
            data1.addFirst(Integer.parseInt(ans.getVips().toString()));
            data2.addFirst(Integer.parseInt(ans.getAuthors().toString()));
            data3.addFirst(Integer.parseInt(ans.getBooks().toString()));
        }
        serie serie1 = new serie("会员数量", data1);
        serie serie2 = new serie("作家数量", data2);
        serie serie3 = new serie("作品数量", data3);
        series.add(JSON.toJSONString(serie1));
        series.add(JSON.toJSONString(serie2));
        series.add(JSON.toJSONString(serie3));
    }

    private void updateAllBooksInModel(BookParam bookParam, Model model, int page, int pageSize) {
        PageInfo<Book> allBooks = bookService.getFuzzyQueryAllBooks(page, pageSize, bookParam);
        model.addAttribute("allBooks", allBooks);
    }
}
