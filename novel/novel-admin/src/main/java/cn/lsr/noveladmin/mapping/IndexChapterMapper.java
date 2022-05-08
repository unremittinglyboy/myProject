package cn.lsr.noveladmin.mapping;

import cn.lsr.noveladmin.model.IndexChapter;
import cn.lsr.noveladmin.model.IndexChapterExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IndexChapterMapper {
    long countByExample(IndexChapterExample example);

    int deleteByExample(IndexChapterExample example);

    int deleteByPrimaryKey(Long id);

    int insert(IndexChapter record);

    int insertSelective(IndexChapter record);

    List<IndexChapter> selectByExampleWithBLOBs(IndexChapterExample example);

    List<IndexChapter> selectByExample(IndexChapterExample example);

    IndexChapter selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") IndexChapter record, @Param("example") IndexChapterExample example);

    int updateByExampleWithBLOBs(@Param("record") IndexChapter record, @Param("example") IndexChapterExample example);

    int updateByExample(@Param("record") IndexChapter record, @Param("example") IndexChapterExample example);

    int updateByPrimaryKeySelective(IndexChapter record);

    int updateByPrimaryKeyWithBLOBs(IndexChapter record);

    int updateByPrimaryKey(IndexChapter record);
}
