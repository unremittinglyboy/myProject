package cn.lsr.noveladmin.controller;

import cn.lsr.noveladmin.Service.BookService;
import cn.lsr.noveladmin.Service.ChapterService;
import cn.lsr.noveladmin.Service.IndexService;
import cn.lsr.noveladmin.model.Book;
import cn.lsr.noveladmin.model.BookIndex;
import cn.lsr.noveladmin.model.IndexChapter;
import cn.lsr.noveladmin.util.FileNameUtils;
import cn.lsr.noveladmin.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/catalogue")
public class CatalogueController {

    @Autowired
    private BookService bookService;

    @Autowired
    private IndexService indexService;

    @Autowired
    private ChapterService chapterService;

    @Value("${web.upload-path}")
    private String path;

    @GetMapping("/catalogForward/{bookId}")
    public ModelAndView catalogForward(@PathVariable Long bookId){
        Book curBook = bookService.getOne(bookId);
        BookIndex bookIndex = indexService.selectIndexByBookId(bookId);
        List<IndexChapter> bookChapters = chapterService.getAllChaptersByIndexId(bookIndex.getId());
        Collections.sort(bookChapters, new Comparator<IndexChapter>() {
            @Override
            public int compare(IndexChapter o1, IndexChapter o2) {
                return (int)(o1.getId() - o2.getId());
            }
        });
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("curBook", curBook);
        modelAndView.addObject("bookIndex", bookIndex);
        modelAndView.addObject("bookChapters", bookChapters);
        modelAndView.setViewName("cata");
        return modelAndView;
    }
    @GetMapping("/catalogForward")
    public ModelAndView catalogForward2(@RequestParam Long bookId){
        Book curBook = bookService.getOne(bookId);
        BookIndex bookIndex = indexService.selectIndexByBookId(bookId);
        List<IndexChapter> bookChapters = chapterService.getAllChaptersByIndexId(bookIndex.getId());
        Collections.sort(bookChapters, new Comparator<IndexChapter>() {
            @Override
            public int compare(IndexChapter o1, IndexChapter o2) {
                return (int)(o1.getId() - o2.getId());
            }
        });
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("curBook", curBook);
        modelAndView.addObject("bookIndex", bookIndex);
        modelAndView.addObject("bookChapters", bookChapters);
        modelAndView.setViewName("cata");
        return modelAndView;
    }

    @PostMapping("/catalogPictureChange/{bookId}")
    public ModelAndView catalogPictureChange(@RequestParam("picUrlFile") MultipartFile file, @RequestParam("beforeChangePicture") String beforeChangePicture, @PathVariable Long bookId){
        String oldFileName = FileUtils.retainPicName(beforeChangePicture);
        String oldRealPath = path + "/" + oldFileName;
        List<String> ansNameAndRealPath = FileUtils.upload(path, file.getOriginalFilename());
        String newFileName = ansNameAndRealPath.get(0);
        String newRealPath = ansNameAndRealPath.get(1);
        if(FileUtils.deletePic(oldRealPath)) {
            FileUtils.genPic(file, newRealPath);
            Book book = bookService.getOne(bookId);
            book.setLastIndexUpdateTime(new Date());
            book.setPicUrl("/images/" + newFileName);
            int i = bookService.updateBook(book);
        }else{
            throw new RuntimeException("图片更换失败！");
        }
        ModelAndView modelAndView = catalogForward(bookId);
        return modelAndView;
    }

    @PostMapping("/catalogDescChange/{bookId}")
    public ModelAndView catalogDescChange(@PathVariable Long bookId, @RequestParam("bookDesc") String bookDesc){
        Book book = bookService.getOne(bookId);
        book.setLastIndexUpdateTime(new Date());
        book.setBookDesc(bookDesc);
        int i = bookService.updateBook(book);
        ModelAndView modelAndView = catalogForward(bookId);
        return modelAndView;
    }

    @GetMapping("/catalogDelete/{bookId}")
    public String catalogDelete(@PathVariable Long bookId){
        int i = bookService.deleteBook(bookId);
        return "redirect:/catalogue/toIndex";
    }

    @GetMapping("/toIndex")
    public String toIndex(Model model){
        return "forward:/admin/index";
    }
}
