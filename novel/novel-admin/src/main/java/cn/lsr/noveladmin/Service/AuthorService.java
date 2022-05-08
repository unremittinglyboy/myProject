package cn.lsr.noveladmin.Service;

import cn.lsr.noveladmin.model.Author;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AuthorService {
    Author getByName(String Name);
    List<Author> getAll();
    Author getOne(Long id);
    Integer GetAllAuthorsCount();
    PageInfo<Author> getAllAuthors(Integer page, Integer pageSize);
    List<Author> fuzzyQuery(String querySentence);
    PageInfo<Author> getFuzzyQueryAllAuthors(Integer page, Integer pageSize, String querySequence);
    int deleteAuthor(Long id);
    int insertAuthor(Author author);
    int updateAuthor(Author author);
    List<String> getAllPenName();
    List<String> getAllPenNameNotMe(String MeName);
}
