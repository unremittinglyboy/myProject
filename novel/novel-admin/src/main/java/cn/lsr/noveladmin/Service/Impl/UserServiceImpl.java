package cn.lsr.noveladmin.Service.Impl;

import cn.lsr.noveladmin.Service.UserService;
import cn.lsr.noveladmin.mapping.UserMapper;
import cn.lsr.noveladmin.model.User;
import cn.lsr.noveladmin.model.UserExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    private String pre = "user_";

    @Override
    public List<User> getAll() {
        return userMapper.getAll();
    }

    /**
     * 根据ID搜索策略：先查询redis是否存在对应id的key，不存在则查数据库->更新缓存，存在则直接从缓存中取出数据。
     * @param id
     * @return
     */
    @Override
    public User getOne(Long id) {
        String key = pre + id;
        User user = null;
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        boolean hasKey = redisTemplate.hasKey(key);
        if(hasKey){
            user = operations.get(key);
            return user;
        }else{
            user = userMapper.selectByPrimaryKey(id);
            operations.set(key, user, 5, TimeUnit.HOURS);
            return user;
        }
    }

    @Override
    public Integer GetAllUsersCount() {
        int countUsers = 0;
        countUsers = (int)userMapper.countByExample(new UserExample());
        return new Integer(countUsers);
    }

    @Override
    public PageInfo<User> getAllUsers(Integer page, Integer pageSize) {
        if(page == null){
            page = 1; //设置默认当前页
        }
        if(page <= 0){
            page = 1;
        }
        if(pageSize == null){
            pageSize = 5;//设置默认每页显示的数据条数
        }

        PageInfo<User> pageInfo = null;
        try {
            PageHelper.startPage(page, pageSize);
            List<User> userList = getAll();
            pageInfo = new PageInfo<User>(userList);
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
    public int deleteUser(Long id) {
        int result = userMapper.deleteByPrimaryKey(id);
        String key = pre + id;
        if(result != 0){
            boolean hasKey = redisTemplate.hasKey(key);
            if(hasKey) {
                redisTemplate.delete(key);
            }
        }
        return result;
    }

    public int insertUser(User user){
        if(user.getUserType() == null) user.setUserType((byte)0);
        if(user.getStatus() == null) user.setStatus((byte)0);
        user.setUserPassword(DigestUtils.md5DigestAsHex(user.getUserPassword().getBytes()));
        user.setCreateTime(new Date());
        return userMapper.insert(user);
    }

    /**
     * 更新策略：在数据库中进行更新，成功则检测是否存在对应key，存在则删除后加入新的，不存在直接加入。
     * @param user
     * @return
     */
    @Override
    public int updateUser(User user) {
        user.setUserPassword(DigestUtils.md5DigestAsHex(user.getUserPassword().getBytes()));
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        int result = userMapper.updateByPrimaryKey(user);
        if(result != 0){
            String key = pre + user.getId();
            boolean hasKey = redisTemplate.hasKey(key);
            if(hasKey){
                redisTemplate.delete(key);
            }
            User newUser= getOne(user.getId());
            if(newUser != null){
                operations.set(key, newUser, 3, TimeUnit.HOURS);
            }
        }
        return result;
    }


    @Override
    public PageInfo<User> getFuzzyQueryAllUsers(Integer page, Integer pageSize, String querySequence) {
        if(page == null){
            page = 1; //设置默认当前页
        }
        if(page <= 0){
            page = 1;
        }
        if(pageSize == null){
            pageSize = 5;//设置默认每页显示的数据条数
        }

        PageInfo<User> pageInfo = null;
        try {
            PageHelper.startPage(page, pageSize);
            List<User> userList = fuzzyQuery(querySequence);
            pageInfo = new PageInfo<User>(userList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PageHelper.clearPage();
        }
        return pageInfo;
    }

    @Override
    public List<User> fuzzyQuery(String querySequence) {
        if(querySequence == null || querySequence.length() == 0) return null;
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserNameEqualTo(querySequence);
        List<User> users = userMapper.selectByExample(userExample);
        return users;
    }

    @Override
    public List<String> getAllUserName() {
        List<User> users = userMapper.getAll();
        List<String> userNameList = new ArrayList<>();
        for(User user : users){
            userNameList.add(user.getUserName());
        }
        return userNameList;
    }


    public List<String> getAllUserNameNotMe(String MeName) {
        List<User> users = userMapper.getAll();
        List<String> userNameList = new ArrayList<>();
        for(User user : users){
            if(user.getUserName().equals(MeName)) continue;
            userNameList.add(user.getUserName());
        }
        return userNameList;
    }

    @Override
    public Integer getAllVips() {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserTypeEqualTo((byte)1);
        int countVips = (int)userMapper.countByExample(userExample);
        return countVips;
    }
}
