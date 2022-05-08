package cn.lsr.noveladmin.Service;

import cn.lsr.noveladmin.model.Type;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TypeService {
    List<Type> getAll();
    Type getOne(Integer id);
    Integer GetAllTypesCount();
    PageInfo<Type> getAllTypes(Integer page, Integer pageSize);
    List<Type> fuzzyQuery(String querySentence);
    PageInfo<Type> getFuzzyQueryAllTypes(Integer page, Integer pageSize, String querySequence);
    int deleteType(Integer id);
    int insertType(Type type);
    int updateType(Type type);
    List<String> getAllTypeName();
}
