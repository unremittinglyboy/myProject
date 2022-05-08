package cn.lsr.noveladmin.Service.Impl;

import cn.lsr.noveladmin.Service.AdministratorService;
import cn.lsr.noveladmin.mapping.AdministratorMapper;
import cn.lsr.noveladmin.model.Administrator;
import cn.lsr.noveladmin.model.AdministratorExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class AdministratorServiceImpl implements AdministratorService {
    @Autowired
    private AdministratorMapper administratorMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    private String pre = "administrator_";

    @Override
    public List<Administrator> getAll() {
        return administratorMapper.getAll();
    }

    /**
     * 根据ID搜索策略：先查询redis是否存在对应id的key，不存在则查数据库->更新缓存，存在则直接从缓存中取出数据。
     * @param id
     * @return
     */
    @Override
    public Administrator getOne(Long id) {
        String key = pre + id;
        Administrator administrator = null;
        ValueOperations<String, Administrator> operations = redisTemplate.opsForValue();
        boolean hasKey = redisTemplate.hasKey(key);
        if(hasKey){
            administrator = operations.get(key);
            return administrator;
        }else{
            administrator = administratorMapper.selectByPrimaryKey(id);
            operations.set(key, administrator, 5, TimeUnit.HOURS);
            return administrator;
        }
    }

    @Override
    public Integer GetAllAdministratorsCount() {
        int countAdministrators = 0;
        countAdministrators = (int)administratorMapper.countByExample(new AdministratorExample());
        return new Integer(countAdministrators);
    }

    @Override
    public PageInfo<Administrator> getAllAdministrators(Integer page, Integer pageSize) {
        if(page == null){
            page = 1; //设置默认当前页
        }
        if(page <= 0){
            page = 1;
        }
        if(pageSize == null){
            pageSize = 5;//设置默认每页显示的数据条数
        }

        PageInfo<Administrator> pageInfo = null;
        try {
            PageHelper.startPage(page, pageSize);
            List<Administrator> administratorList = getAll();
            pageInfo = new PageInfo<Administrator>(administratorList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PageHelper.clearPage();
        }
        return pageInfo;
    }

    @Override
    public List<Administrator> fuzzyQuery(String querySequence) {
        if(querySequence == null || querySequence.length() == 0) return null;
        querySequence = querySequence.trim();
        String[] split = querySequence.split("&");
        int n = split.length;
        AdministratorExample administratorExample = new AdministratorExample();
        AdministratorExample.Criteria criteria = administratorExample.createCriteria();
        if(n >= 1) if(!split[0].equals("")) criteria.andAdminNameLike(split[0] + "%");
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
        return administratorMapper.selectByExample(administratorExample);
    }

    @Override
    public PageInfo<Administrator> getFuzzyQueryAllAdministrators(Integer page, Integer pageSize, String querySequence) {
        if(page == null){
            page = 1; //设置默认当前页
        }
        if(page <= 0){
            page = 1;
        }
        if(pageSize == null){
            pageSize = 5;//设置默认每页显示的数据条数
        }

        PageInfo<Administrator> pageInfo = null;
        try {
            PageHelper.startPage(page, pageSize);
            List<Administrator> administratorList = fuzzyQuery(querySequence);
            pageInfo = new PageInfo<Administrator>(administratorList);
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
    public int deleteAdministrator(Long id) {
        int result = administratorMapper.deleteByPrimaryKey(id);
        String key = pre + id;
        if(result != 0){
            boolean hasKey = redisTemplate.hasKey(key);
            if(hasKey) {
                redisTemplate.delete(key);
            }
        }
        return result;
    }

    public int insertAdministrator(Administrator administrator){
        administrator.setCreateTime(new Date());
        administrator.setAdminPassword(DigestUtils.md5DigestAsHex(administrator.getAdminPassword().getBytes()));
        return administratorMapper.insert(administrator);
    }

    /**
     * 更新策略：在数据库中进行更新，成功则检测是否存在对应key，存在则删除后加入新的，不存在直接加入。
     * @param administrator
     * @return
     */
    @Override
    public int updateAdministrator(Administrator administrator) {
        administrator.setAdminPassword(DigestUtils.md5DigestAsHex(administrator.getAdminPassword().getBytes()));
        ValueOperations<String, Administrator> operations = redisTemplate.opsForValue();
        int result = administratorMapper.updateByPrimaryKey(administrator);
        if(result != 0){
            String key = pre + administrator.getId();
            boolean hasKey = redisTemplate.hasKey(key);
            if(hasKey){
                redisTemplate.delete(key);
            }
            Administrator newAdministrator= getOne(administrator.getId());
            if(newAdministrator != null){
                operations.set(key, newAdministrator, 3, TimeUnit.HOURS);
            }
        }
        return result;
    }

    @Override
    public List<String> getAllAdminName() {
        List<Administrator> all = getAll();
        List<String> administratorNames = new ArrayList<>();
        for(Administrator administrator : all){
            administratorNames.add(administrator.getAdminName());
        }
        return administratorNames;
    }

    public List<String> getAllAdminNameNotMe(String MeName) {
        List<Administrator> all = getAll();
        List<String> administratorNames = new ArrayList<>();
        for(Administrator administrator : all){
            if(administrator.getAdminName().equals(MeName)) continue;
            administratorNames.add(administrator.getAdminName());
        }
        return administratorNames;
    }
}
