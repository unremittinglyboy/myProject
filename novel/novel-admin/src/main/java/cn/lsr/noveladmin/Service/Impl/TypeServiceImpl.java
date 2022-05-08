package cn.lsr.noveladmin.Service.Impl;

import cn.lsr.noveladmin.Service.TypeService;
import cn.lsr.noveladmin.mapping.TypeMapper;
import cn.lsr.noveladmin.model.Type;
import cn.lsr.noveladmin.model.TypeExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    private String pre = "type_";

    @Override
    public List<Type> getAll() {
        return typeMapper.getAll();
    }

    /**
     * 根据ID搜索策略：先查询redis是否存在对应id的key，不存在则查数据库->更新缓存，存在则直接从缓存中取出数据。
     * @param id
     * @return
     */
    @Override
    public Type getOne(Integer id) {
        String key = pre + id;
        Type type = null;
        ValueOperations<String, Type> operations = redisTemplate.opsForValue();
        boolean hasKey = redisTemplate.hasKey(key);
        if(hasKey){
            type = operations.get(key);
            return type;
        }else{
            type = typeMapper.selectByPrimaryKey(id);
            operations.set(key, type, 5, TimeUnit.HOURS);
            return type;
        }
    }

    @Override
    public Integer GetAllTypesCount() {
        int countTypes = 0;
        countTypes = (int)typeMapper.countByExample(new TypeExample());
        return new Integer(countTypes);
    }

    @Override
    public PageInfo<Type> getAllTypes(Integer page, Integer pageSize) {
        if(page == null){
            page = 1; //设置默认当前页
        }
        if(page <= 0){
            page = 1;
        }
        if(pageSize == null){
            pageSize = 5;//设置默认每页显示的数据条数
        }

        PageInfo<Type> pageInfo = null;
        try {
            PageHelper.startPage(page, pageSize);
            List<Type> typeList = getAll();
            pageInfo = new PageInfo<Type>(typeList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PageHelper.clearPage();
        }
        return pageInfo;
    }

    @Override
    public List<Type> fuzzyQuery(String querySequence) {
        if(querySequence == null || querySequence.length() == 0) return null;
        querySequence = querySequence.trim();
        String[] split = querySequence.split("&");
        int n = split.length;
        TypeExample typeExample = new TypeExample();
        TypeExample.Criteria criteria = typeExample.createCriteria();
        if(n >= 1) if(!split[0].equals("")) criteria.andTypeNameLike(split[0] + "%");
        if(n >= 2){
            if(!split[1].equals("")){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                Date date = null;
                try {
                    date = sdf.parse(split[1]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                criteria.andCreateTimeEqualTo(date == null ? null : date);
            }
        }
        return typeMapper.selectByExample(typeExample);
    }

    @Override
    public PageInfo<Type> getFuzzyQueryAllTypes(Integer page, Integer pageSize, String querySequence) {
        if(page == null){
            page = 1; //设置默认当前页
        }
        if(page <= 0){
            page = 1;
        }
        if(pageSize == null){
            pageSize = 5;//设置默认每页显示的数据条数
        }

        PageInfo<Type> pageInfo = null;
        try {
            PageHelper.startPage(page, pageSize);
            List<Type> typeList = fuzzyQuery(querySequence);
            pageInfo = new PageInfo<Type>(typeList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PageHelper.clearPage();
        }
        return pageInfo;
    }


    /**
     * 根据ID删除策略：删除数据表中的数据，然后删除缓存
     * @param id
     * @return
     */
    @Override
    public int deleteType(Integer id) {
        int result = typeMapper.deleteByPrimaryKey(id);
        String key = pre + id;
        if(result != 0){
            boolean hasKey = redisTemplate.hasKey(key);
            if(hasKey) {
                redisTemplate.delete(key);
            }
        }
        return result;
    }

    public int insertType(Type type){
        type.setCreateTime(new Date());
        return typeMapper.insert(type);
    }

    /**
     * 更新策略：在数据库中进行更新，成功则检测是否存在对应key，存在则删除后加入新的，不存在直接加入。
     * @param type
     * @return
     */
    @Override
    public int updateType(Type type) {
        ValueOperations<String, Type> operations = redisTemplate.opsForValue();
        int result = typeMapper.updateByPrimaryKey(type);
        if(result != 0){
            String key = pre + type.getId();
            boolean hasKey = redisTemplate.hasKey(key);
            if(hasKey){
                redisTemplate.delete(key);
            }
            Type newType= getOne(type.getId());
            if(newType != null){
                operations.set(key, newType, 3, TimeUnit.HOURS);
            }
        }
        return result;
    }

    @Override
    public List<String> getAllTypeName() {
        List<Type> all = getAll();
        List<String> typeNames = new ArrayList<>();
        for(Type type : all){
            typeNames.add(type.getTypeName());
        }
        return typeNames;
    }
}
