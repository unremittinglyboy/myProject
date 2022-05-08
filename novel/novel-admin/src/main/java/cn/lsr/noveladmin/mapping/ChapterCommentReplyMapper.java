package cn.lsr.noveladmin.mapping;

import cn.lsr.noveladmin.model.ChapterCommentReply;
import cn.lsr.noveladmin.model.ChapterCommentReplyExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ChapterCommentReplyMapper {
    long countByExample(ChapterCommentReplyExample example);

    int deleteByExample(ChapterCommentReplyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ChapterCommentReply record);

    int insertSelective(ChapterCommentReply record);

    List<ChapterCommentReply> selectByExample(ChapterCommentReplyExample example);

    ChapterCommentReply selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ChapterCommentReply record, @Param("example") ChapterCommentReplyExample example);

    int updateByExample(@Param("record") ChapterCommentReply record, @Param("example") ChapterCommentReplyExample example);

    int updateByPrimaryKeySelective(ChapterCommentReply record);

    int updateByPrimaryKey(ChapterCommentReply record);
}
