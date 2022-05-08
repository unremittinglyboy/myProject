package cn.lsr.noveladmin.Service;

import cn.lsr.noveladmin.model.Tag;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TagService {
    List<Tag> getAll();
    Tag getOne(Integer id);
    Integer GetAllTagsCount();
    PageInfo<Tag> getAllTags(Integer page, Integer pageSize);
    List<Tag> fuzzyQuery(String querySentence);
    PageInfo<Tag> getFuzzyQueryAllTags(Integer page, Integer pageSize, String querySequence);
    int deleteTag(Integer id);
    int insertTag(Tag tag);
    int updateTag(Tag tag);
    List<String> getAllTagName();
}
