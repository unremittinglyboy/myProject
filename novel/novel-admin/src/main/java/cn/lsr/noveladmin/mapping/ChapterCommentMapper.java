package cn.lsr.noveladmin.mapping;

import cn.lsr.noveladmin.model.ChapterComment;
import cn.lsr.noveladmin.model.ChapterCommentExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ChapterCommentMapper {
    long countByExample(ChapterCommentExample example);

    int deleteByExample(ChapterCommentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ChapterComment record);

    int insertSelective(ChapterComment record);

    List<ChapterComment> selectByExample(ChapterCommentExample example);

    ChapterComment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ChapterComment record, @Param("example") ChapterCommentExample example);

    int updateByExample(@Param("record") ChapterComment record, @Param("example") ChapterCommentExample example);

    int updateByPrimaryKeySelective(ChapterComment record);

    int updateByPrimaryKey(ChapterComment record);
}
