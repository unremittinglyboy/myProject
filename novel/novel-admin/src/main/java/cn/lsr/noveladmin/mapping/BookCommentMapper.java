package cn.lsr.noveladmin.mapping;

import cn.lsr.noveladmin.model.BookComment;
import cn.lsr.noveladmin.model.BookCommentExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BookCommentMapper {
    long countByExample(BookCommentExample example);

    int deleteByExample(BookCommentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BookComment record);

    int insertSelective(BookComment record);

    List<BookComment> selectByExample(BookCommentExample example);

    BookComment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BookComment record, @Param("example") BookCommentExample example);

    int updateByExample(@Param("record") BookComment record, @Param("example") BookCommentExample example);

    int updateByPrimaryKeySelective(BookComment record);

    int updateByPrimaryKey(BookComment record);
}
