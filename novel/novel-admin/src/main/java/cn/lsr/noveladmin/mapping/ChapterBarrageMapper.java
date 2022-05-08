package cn.lsr.noveladmin.mapping;

import cn.lsr.noveladmin.model.ChapterBarrage;
import cn.lsr.noveladmin.model.ChapterBarrageExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ChapterBarrageMapper {
    long countByExample(ChapterBarrageExample example);

    int deleteByExample(ChapterBarrageExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ChapterBarrage record);

    int insertSelective(ChapterBarrage record);

    List<ChapterBarrage> selectByExample(ChapterBarrageExample example);

    ChapterBarrage selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ChapterBarrage record, @Param("example") ChapterBarrageExample example);

    int updateByExample(@Param("record") ChapterBarrage record, @Param("example") ChapterBarrageExample example);

    int updateByPrimaryKeySelective(ChapterBarrage record);

    int updateByPrimaryKey(ChapterBarrage record);
}
