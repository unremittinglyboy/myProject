package cn.lsr.noveladmin.Service;

import cn.lsr.noveladmin.model.Administrator;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdministratorService {
    List<Administrator> getAll();
    Administrator getOne(Long id);
    Integer GetAllAdministratorsCount();
    PageInfo<Administrator> getAllAdministrators(Integer page, Integer pageSize);
    List<Administrator> fuzzyQuery(String querySentence);
    PageInfo<Administrator> getFuzzyQueryAllAdministrators(Integer page, Integer pageSize, String querySequence);
    int deleteAdministrator(Long id);
    int insertAdministrator(Administrator administrator);
    int updateAdministrator(Administrator administrator);
    List<String> getAllAdminName();
    List<String> getAllAdminNameNotMe(String MeName);
}
