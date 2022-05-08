package cn.lsr.noveladmin.Service.Impl;

import cn.lsr.noveladmin.Service.AdministratorService;
import cn.lsr.noveladmin.Service.AdminLoginService;
import cn.lsr.noveladmin.model.Administrator;
import cn.lsr.noveladmin.model.Permissions;
import cn.lsr.noveladmin.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class AdminLoginServiceImpl implements AdminLoginService {
    @Autowired
    private AdministratorService administratorService;

    @Override
    public Administrator getAdministratorByName(String getMapByName) {
        return getMapByAdminName(getMapByName);
    }

    @Override
    public Administrator getMapByAdminName(String administratorName) {
        //权限设置
        Permissions permissions1 = new Permissions("1", "query");
        Permissions permissions2 = new Permissions("2", "add");
        Set<Permissions> permissionsSet = new HashSet<>();
        permissionsSet.add(permissions1);
        permissionsSet.add(permissions2);
        //角色对应权限的角色设置
        Role role = new Role("1", "admin", permissionsSet);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        //拥有某角色的客户设置
        List<Administrator> administrators = administratorService.getAll();
        HashMap<String, Administrator> AdminMap = new HashMap<>();
        for(Administrator a : administrators){
            if(a != null){
                a.setRoles(roleSet);
                AdminMap.put(a.getAdminName(), a);
            }
        }
        return AdminMap.get(administratorName);
    }
}
