package cn.lsr.noveladmin.shiro;

import cn.lsr.noveladmin.Service.AdminLoginService;
import cn.lsr.noveladmin.model.Administrator;
import cn.lsr.noveladmin.model.Permissions;
import cn.lsr.noveladmin.model.Role;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class AdminCustomRealm extends AuthorizingRealm {

    @Autowired
    private AdminLoginService adminLoginService;

    /**
     * 权限配置类
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        String name = (String) principalCollection.getPrimaryPrincipal();
        //查询用户名称
        Administrator administrator = adminLoginService.getAdministratorByName(name);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for(Role role : administrator.getRoles()){
            //添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            //添加权限
            for(Permissions permissions : role.getPermissions()){
                simpleAuthorizationInfo.addStringPermission(permissions.getPermissionsName());
            }
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 认证配置类
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if(StringUtils.isEmpty(authenticationToken.getPrincipal())){
            return null;
        }
        //获取用户信息
        String name = authenticationToken.getPrincipal().toString();
        Administrator administrator = adminLoginService.getAdministratorByName(name);
        if(administrator == null){
            //这里返回会报出对应异常
            return null;
        } else{
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, administrator.getAdminPassword(), this.getName());
            return simpleAuthenticationInfo;
        }
    }
}
