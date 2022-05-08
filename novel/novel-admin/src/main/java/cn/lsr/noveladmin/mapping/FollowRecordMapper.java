package cn.lsr.noveladmin.mapping;

import cn.lsr.noveladmin.model.FollowRecordExample;
import cn.lsr.noveladmin.model.FollowRecordKey;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FollowRecordMapper {
    long countByExample(FollowRecordExample example);

    int deleteByExample(FollowRecordExample example);

    int deleteByPrimaryKey(FollowRecordKey key);

    int insert(FollowRecordKey record);

    int insertSelective(FollowRecordKey record);

    List<FollowRecordKey> selectByExample(FollowRecordExample example);

    int updateByExampleSelective(@Param("record") FollowRecordKey record, @Param("example") FollowRecordExample example);

    int updateByExample(@Param("record") FollowRecordKey record, @Param("example") FollowRecordExample example);
}
