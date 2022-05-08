package cn.lsr.noveladmin.Service.Impl;

import cn.lsr.noveladmin.Service.IndexService;
import cn.lsr.noveladmin.mapping.BookIndexMapper;
import cn.lsr.noveladmin.model.Book;
import cn.lsr.noveladmin.model.BookIndex;
import cn.lsr.noveladmin.model.BookIndexExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private BookIndexMapper bookIndexMapper;

    @Override
    public int insertIndexByBookId(Book book) {
        if(book == null) return -1;
        String defaultCatalogueName = book.getBookName() + "主目录";
        BookIndex bookIndex = new BookIndex();
        bookIndex.setBookId(book.getId());
        bookIndex.setIndexName(defaultCatalogueName);
        bookIndex.setChapterCount(new Long(0));
        return bookIndexMapper.insert(bookIndex);
    }

    @Override
    public int deleteIndexByBookId(Long id) {
        BookIndexExample bookIndexExample = new BookIndexExample();
        BookIndexExample.Criteria criteria = bookIndexExample.createCriteria();
        criteria.andBookIdEqualTo(id);
        return bookIndexMapper.deleteByExample(bookIndexExample);
    }

    @Override
    public Long selectIndexIdByBookId(Long bookId) {
        BookIndexExample bookIndexExample = new BookIndexExample();
        BookIndexExample.Criteria criteria = bookIndexExample.createCriteria();
        criteria.andBookIdEqualTo(bookId);
        List<BookIndex> bookIndices = bookIndexMapper.selectByExample(bookIndexExample);
        return bookIndices == null || bookIndices.size() == 0 ? null : bookIndices.get(0).getId();
    }

    @Override
    public BookIndex selectIndexByBookId(Long bookId) {
        BookIndexExample bookIndexExample = new BookIndexExample();
        BookIndexExample.Criteria criteria = bookIndexExample.createCriteria();
        criteria.andBookIdEqualTo(bookId);
        List<BookIndex> bookIndices = bookIndexMapper.selectByExample(bookIndexExample);
        return bookIndices == null || bookIndices.size() == 0 ? null : bookIndices.get(0);
    }

    @Override
    public int deleteIndexById(Long id) {
        return bookIndexMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Long getBookIdByIndexId(Long id) {
        BookIndex bookIndex = bookIndexMapper.selectByPrimaryKey(id);
        return bookIndex.getBookId();
    }


}
