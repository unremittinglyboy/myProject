package cn.lsr.noveladmin.Service;

import cn.lsr.noveladmin.model.Book;
import cn.lsr.noveladmin.model.BookIndex;

public interface IndexService {
    int insertIndexByBookId(Book book);
    int deleteIndexByBookId(Long id);
    Long selectIndexIdByBookId(Long bookId);
    BookIndex selectIndexByBookId(Long bookId);
    int deleteIndexById(Long id);
    Long getBookIdByIndexId(Long id);
}
