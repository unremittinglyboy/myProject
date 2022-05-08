package cn.lsr.noveladmin.Service;

import cn.lsr.noveladmin.RequestParam.BookParam;
import cn.lsr.noveladmin.model.Book;
import com.github.pagehelper.PageInfo;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;

public interface BookService {
    Book findById(Long id);
    Integer getAllBooksCount();
    SearchResponse getAll();
    Book getOne(Long id);
    public SearchResponse search(BookParam bookParam, Integer page, Integer pageSize);
    PageInfo<Book> getFuzzyQueryAllBooks(Integer page, Integer pageSize, BookParam bookParam);
    int deleteBook(Long id);
    int insertBook(Book book);
    int insertBook(Book book, RabbitTemplate rabbitTemplate);
    int updateBook(Book book);
    List<String> getAllBookName();
    List<String> getAllBookNameNotMe(String MeName);
}
