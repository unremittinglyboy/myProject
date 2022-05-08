package cn.lsr.noveladmin.controller;

import cn.lsr.noveladmin.Service.BookService;
import cn.lsr.noveladmin.Service.ChapterService;
import cn.lsr.noveladmin.Service.IndexService;
import cn.lsr.noveladmin.model.Book;
import cn.lsr.noveladmin.model.BookIndex;
import cn.lsr.noveladmin.model.IndexChapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/chapter")
public class ChapterController {

    @Autowired
    private BookService bookService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private IndexService indexService;

    @GetMapping("/chapterInsert/{bookId}")
    public String chapterInsert(@PathVariable(name = "bookId") Long bookId, Model model){
        BookIndex curBookIndex = indexService.selectIndexByBookId(bookId);
        model.addAttribute("curBookIndex", curBookIndex);
        return "chapterInsert";
    }
    @GetMapping("/chapterInsert")
    public String chapterInsert2(@RequestParam(name = "bookId") Long bookId, Model model){
        BookIndex curBookIndex = indexService.selectIndexByBookId(bookId);
        model.addAttribute("curBookIndex", curBookIndex);
        return "chapterInsert";
    }

    @PostMapping("/chapterInsertMessage")
    public String chapterInsertMessage(@Validated IndexChapter chapter, Errors errors, RedirectAttributes redirectAttributes){
        Long indexId = chapter.getIndexId();
        Long bookId = indexService.getBookIdByIndexId(indexId);
        if(errors.hasErrors()){
            List<String> chapterInsertErrors = new ArrayList<>();
            List<ObjectError> allErrors = errors.getAllErrors();
            for(ObjectError error : allErrors){
                String defaultMessage = error.getDefaultMessage();
                chapterInsertErrors.add(defaultMessage);
            }
            redirectAttributes.addFlashAttribute("chapterInsertErrors", chapterInsertErrors);
            return "redirect:/chapter/chapterInsert?bookId=" + bookId;
        }
        int i = chapterService.insertChapter(chapter);
        Book book = bookService.getOne(bookId);
        book.setLastIndexUpdateTime(new Date());
        book.setWordCount(book.getWordCount() + chapter.getChapterContent().length());
        bookService.updateBook(book);
        return "redirect:/chapter/toCata?bookId=" + bookId;
    }

    @GetMapping("/chapterEdit/{chapterId}")
    public String chapterEdit(@PathVariable(name = "chapterId")Long chapterId, Model model){
        IndexChapter chapter = chapterService.getByChapterId(chapterId);
        model.addAttribute("chapter", chapter);
        return "chapterEdit";
    }

    @GetMapping("/chapterEdit")
    public String chapterEdit2(@RequestParam(name = "chapterId")Long chapterId, Model model){
        IndexChapter chapter = chapterService.getByChapterId(chapterId);
        model.addAttribute("chapter", chapter);
        return "chapterEdit";
    }

    @PostMapping("/chapterEditMessage")
    public String chapterEditMessage(@ModelAttribute @Validated IndexChapter chapter, Errors errors, RedirectAttributes redirectAttributes){
        IndexChapter curChapter = chapterService.getByChapterId(chapter.getId());
        Long indexId = curChapter.getIndexId();
        Long bookId = indexService.getBookIdByIndexId(indexId);
        chapter.setIndexId(indexId);
        if(errors.hasErrors()){
            List<String> chapterEditErrors = new ArrayList<>();
            List<ObjectError> allErrors = errors.getAllErrors();
            for(ObjectError error : allErrors){
                String defaultMessage = error.getDefaultMessage();
                chapterEditErrors.add(defaultMessage);
            }
            redirectAttributes.addFlashAttribute("chapterEditErrors", chapterEditErrors);
            return "redirect:/chapter/chapterEdit?chapterId=" + curChapter.getId();
        }
        chapterService.updateChapter(chapter);
        Book book = bookService.getOne(bookId);
        book.setLastIndexUpdateTime(new Date());
        book.setWordCount(book.getWordCount() - curChapter.getChapterContent().length() + chapter.getChapterContent().length());
        bookService.updateBook(book);
        return "redirect:/chapter/toCata?bookId=" + bookId;
    }

    @RequestMapping("/chapterDelete")
    public String chapterDelete(@RequestParam(name = "chapterId")Long chapterId,
                                @RequestParam(name = "bookId") Long bookId){
        int i = chapterService.deleteChapterByChapterId(chapterId);
        IndexChapter chapter = chapterService.getByChapterId(chapterId);
        Book book = bookService.getOne(bookId);
        book.setLastIndexUpdateTime(new Date());
        book.setWordCount(book.getWordCount() - chapter.getChapterContent().length());
        bookService.updateBook(book);
        return "redirect:/chapter/toCata?bookId=" + bookId;
    }

    @GetMapping("/toCata")
    public String toCata(@RequestParam Long bookId, Model model, RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("bookId", bookId);
        return "redirect:/catalogue/catalogForward";
    }

}
