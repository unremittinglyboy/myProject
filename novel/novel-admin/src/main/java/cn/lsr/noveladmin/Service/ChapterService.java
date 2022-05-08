package cn.lsr.noveladmin.Service;

import cn.lsr.noveladmin.model.IndexChapter;

import java.util.List;

public interface ChapterService {
    IndexChapter getByChapterId(Long ChapterId);
    List<IndexChapter> getAllChaptersByIndexId(Long IndexId);
    int deleteChaptersByIndexId(Long IndexId);
    int deleteChapterByChapterId(Long ChapterId);
    int insertChapter(IndexChapter chapter);
    int updateChapter(IndexChapter chapter);
}
