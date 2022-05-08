package cn.lsr.noveladmin.mapping;

import cn.lsr.noveladmin.model.BookCommentReply;
import cn.lsr.noveladmin.model.BookCommentReplyExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BookCommentReplyMapper {
    long countByExample(BookCommentReplyExample example);

    int deleteByExample(BookCommentReplyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BookCommentReply record);

    int insertSelective(BookCommentReply record);

    List<BookCommentReply> selectByExample(BookCommentReplyExample example);

    BookCommentReply selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BookCommentReply record, @Param("example") BookCommentReplyExample example);

    int updateByExample(@Param("record") BookCommentReply record, @Param("example") BookCommentReplyExample example);

    int updateByPrimaryKeySelective(BookCommentReply record);

    int updateByPrimaryKey(BookCommentReply record);
}
