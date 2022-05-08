package cn.lsr.noveladmin.Service;

import cn.lsr.noveladmin.model.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {
    Integer getAllVips();
    List<User> getAll();
    User getOne(Long id);
    Integer GetAllUsersCount();
    PageInfo<User> getAllUsers(Integer page, Integer pageSize);
    List<User> fuzzyQuery(String querySentence);
    PageInfo<User> getFuzzyQueryAllUsers(Integer page, Integer pageSize, String querySequence);
    int deleteUser(Long id);
    int insertUser(User user);
    int updateUser(User user);
    List<String> getAllUserName();
    List<String> getAllUserNameNotMe(String MeName);
}
