package cn.lsr.noveladmin.mapping;

import cn.lsr.noveladmin.model.BookIndex;
import cn.lsr.noveladmin.model.BookIndexExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BookIndexMapper {
    long countByExample(BookIndexExample example);

    int deleteByExample(BookIndexExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BookIndex record);

    int insertSelective(BookIndex record);

    List<BookIndex> selectByExample(BookIndexExample example);

    BookIndex selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BookIndex record, @Param("example") BookIndexExample example);

    int updateByExample(@Param("record") BookIndex record, @Param("example") BookIndexExample example);

    int updateByPrimaryKeySelective(BookIndex record);

    int updateByPrimaryKey(BookIndex record);
}
