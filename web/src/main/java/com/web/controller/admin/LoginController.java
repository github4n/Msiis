package com.web.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.util.config.UserConfig;
import com.web.controller.base.BaseController;
import com.web.security.cache.CacheManageUtils;
import com.web.security.cache.CacheNameSpace;
import com.web.security.cache.SpringCacheManagerWrapper;
import com.web.security.cache.UserCacheConf;
import com.web.service.PermissionService;
import com.web.soupe.dto.SoupeWebModel;
import com.web.soupe.web.Permission;
import net.sf.ehcache.Cache;
import org.apache.log4j.Logger;
import com.web.soupe.web.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.ehcache.EhCacheManagerUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = LoginController.PATH)
public class LoginController extends BaseController {
    protected static final String PATH = "/admin";
    private Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    @Qualifier("myCacheManager")
    private CacheManager cacheManager ;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request) {
        return new ModelAndView("admin/login", "message", null);
    }
    @RequestMapping(value ="", method = RequestMethod.GET)
    public ModelAndView logining(HttpServletRequest request) {
        return new ModelAndView("admin/login", "message", null);
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView login1(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        User user = (User) request.getSession().getAttribute(UserConfig.USER_LOGON_SESSION.getCode());
        System.out.println("测试一下git提交");
        if (user == null) {
            modelAndView.setViewName("redirect:/admin/login");
        }else{
         //获取一级菜单
            PermissionService permissionService =this.getServiceManager().getPermissionService();
         List<Permission> permissionList= permissionService.findByParentId(0);
         modelAndView.addObject("navbar",permissionList);
         if(!CollectionUtils.isEmpty(permissionList)){
             Permission  permission =permissionList.get(0);
             List<Permission> childPermisions=permissionService.findByParentId(permission.getId());
             modelAndView.addObject("menu2",childPermisions);
         }
            modelAndView.addObject("user",user);
        }
        modelAndView.setViewName("admin/index");
        return  modelAndView;
    }

    @RequestMapping(value = "/login/submit", method = {RequestMethod.POST})
    @ResponseBody
    public SoupeWebModel submit(
            @RequestParam(value = "userName", required = true) String name,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "rememberMe" ,required=false ,defaultValue = "false") boolean remmeberMe,
            HttpServletRequest request) {
        Map<String, String> model = new HashMap<String, String>();
        UsernamePasswordToken token = null;
        SoupeWebModel soupeWebModel =new SoupeWebModel();
        try {
            User user = (User) request.getSession().getAttribute(UserConfig.USER_LOGON_SESSION.getCode());
            if (user == null) {
                Subject currentUser = SecurityUtils.getSubject();
                token = new UsernamePasswordToken(name, password);
                token.setRememberMe(false);
                currentUser.login(token);
                user=this.getServiceManager().getUserService().findUserByNameAndPassword(name,"",1);
            }
            request.getSession().setAttribute(UserConfig.USER_LOGON_SESSION.getCode(), user);
            soupeWebModel.setSuccess(true);
        } catch (AuthenticationException a) {
            token.clear();
            soupeWebModel.setMessage("登录认证失败...");
        } catch (Exception ex) {
            logger.error("验证登录信息出现系统错误：" + ex.getMessage(), ex);
            soupeWebModel.setMessage("验证登录信息出现系统错误"+ex.getMessage());
        }
        return soupeWebModel;
    }

    @RequestMapping(value = "/logout")
    public String logout(){
        Subject subject =SecurityUtils.getSubject();
        subject.logout();
        return "admin/login";
    }

    @RequestMapping(value = "/error")
    public String error(){
        Subject subject =SecurityUtils.getSubject();
        subject.logout();
        return "admin/error";
    }


}
