package cn.lsr.noveladmin.controller;

import cn.lsr.noveladmin.model.Administrator;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class LoginController {

    //设置springboot默认主页跳转
    @RequestMapping("/")
    public String hello(){
        return "redirect:/admin/index";
    }
    @RequestMapping("/index.html")
    public String hello2(){
        return "redirect:/admin/index";
    }

    //登录
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public String verify(Administrator administrator, Model model, RedirectAttributes attributes) {
        if (StringUtils.isEmpty(administrator.getAdminName()) || StringUtils.isEmpty(administrator.getAdminPassword())) {
            model.addAttribute("message", "管理名和密码错误");
            return "login";
        }
        String MD5Password = administrator.getAdminPassword();

        //用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(administrator.getAdminName(), DigestUtils.md5DigestAsHex(MD5Password.getBytes()), false);
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
        } catch (UnknownAccountException e) {
            log.error("管理名不存在！", e);
            model.addAttribute("message", "管理名不存在！");
            return "login";
        } catch (AuthenticationException e) {
            log.error("账号或密码错误！", e);
            model.addAttribute("message", "账号或者密码错误！");
            return "login";
        } catch (AuthorizationException e) {
            log.error("没有权限！", e);
            model.addAttribute("message", "没有权限！");
            return "login";
        }

        return "redirect:/admin/index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        try {
            SecurityUtils.getSubject().logout();
        } catch (Exception e) {
            log.error("登出失败", e);
            System.err.println(e.getMessage());
        }
        return "login";
    }
}
