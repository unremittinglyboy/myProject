package cn.lsr.noveladmin.Service.Impl;

import cn.lsr.noveladmin.Service.ChapterService;
import cn.lsr.noveladmin.mapping.IndexChapterMapper;
import cn.lsr.noveladmin.model.IndexChapter;
import cn.lsr.noveladmin.model.IndexChapterExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private IndexChapterMapper indexChapterMapper;

    @Override
    public IndexChapter getByChapterId(Long ChapterId) {
        IndexChapter indexChapter = indexChapterMapper.selectByPrimaryKey(ChapterId);
        return indexChapter;
    }

    @Override
    public List<IndexChapter> getAllChaptersByIndexId(Long IndexId) {
        IndexChapterExample indexChapterExample = new IndexChapterExample();
        IndexChapterExample.Criteria criteria = indexChapterExample.createCriteria();
        criteria.andIndexIdEqualTo(IndexId);
        List<IndexChapter> indexChapters = indexChapterMapper.selectByExample(indexChapterExample);
        return indexChapters;
    }

    @Override
    public int deleteChaptersByIndexId(Long IndexId) {
        IndexChapterExample indexChapterExample = new IndexChapterExample();
        IndexChapterExample.Criteria criteria = indexChapterExample.createCriteria();
        criteria.andIndexIdEqualTo(IndexId);
        return indexChapterMapper.deleteByExample(indexChapterExample);
    }

    @Override
    public int deleteChapterByChapterId(Long ChapterId) {
        int i = indexChapterMapper.deleteByPrimaryKey(ChapterId);
        return i;
    }

    @Override
    public int insertChapter(IndexChapter chapter) {
        int insert = indexChapterMapper.insert(chapter);
        return insert;
    }

    @Override
    public int updateChapter(IndexChapter chapter) {
        int i = indexChapterMapper.updateByPrimaryKey(chapter);
        return i;
    }


}
