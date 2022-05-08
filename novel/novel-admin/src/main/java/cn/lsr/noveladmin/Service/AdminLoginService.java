package cn.lsr.noveladmin.Service;

import cn.lsr.noveladmin.model.Administrator;

public interface AdminLoginService {
    Administrator getAdministratorByName(String getMapByName);
    Administrator getMapByAdminName(String administratorName);
}
