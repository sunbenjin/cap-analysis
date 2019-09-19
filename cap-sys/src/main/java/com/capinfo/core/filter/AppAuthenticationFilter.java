package com.capinfo.core.filter;

import com.alibaba.fastjson.JSONArray;
import com.capinfo.base.CurrentMenu;
import com.capinfo.base.CurrentRole;
import com.capinfo.base.CurrentUser;
import com.capinfo.core.shiro.AppAuthorizingRealm;
import com.capinfo.core.shiro.ShiroUtil;
import com.capinfo.core.token.AppAuthenticationToken;
import com.capinfo.core.utils.Constants;
import com.capinfo.core.utils.SysWebUtils;
import com.capinfo.core.utils.TokenUtils;
import com.capinfo.entity.SysMenu;
import com.capinfo.entity.SysRole;
import com.capinfo.entity.SysUser;
import com.capinfo.exception.InvalidAccessTokenAuthenticationException;
import com.capinfo.exception.LoginRequestMethodNotSupportException;
import com.capinfo.exception.MyException;
import com.capinfo.service.MenuService;
import com.capinfo.service.SysUserService;
import com.capinfo.util.ReType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import java.util.*;

@Service
public class AppAuthenticationFilter extends AuthenticatingFilter {
    private static final String APP_LOGIN_URL = "/api/user/login";
    public static final String DEFAULT_AUTH_ACCESS_TOKEN_PARAM = "token";

    public static final String DEFAULT_USERNAME_PARAM = "username";
    public static final String DEFAULT_PASSWORD_PARAM = "password";

    private String usernameParam = DEFAULT_USERNAME_PARAM;
    private String passwordParam = DEFAULT_PASSWORD_PARAM;

    @Autowired
    private SysUserService userService;

    @Autowired
    private MenuService menuService;
    /**
     * 设置登录的URL
     */
    @Override
    public void setLoginUrl(String loginUrl) {
        super.setLoginUrl(APP_LOGIN_URL);
    }

    /**
     * Shiro登录流程开始前， 创建认证Token
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {

        String username = getUsername(request);
        String password = getPassword(request);

        if (password == null) {
            password = "";
        }

        AppAuthenticationToken appAuthenticationToken = new AppAuthenticationToken(username, password);
        //sudo
        String accessToken = TokenUtils.getAuthHeaderAccessToken(request);

        if (StringUtils.isBlank(accessToken)) {
            accessToken = WebUtils.getCleanParam(request, DEFAULT_AUTH_ACCESS_TOKEN_PARAM);
        }

        if (StringUtils.isNotBlank(accessToken)) {
            appAuthenticationToken.setToken(accessToken);
        }

        return appAuthenticationToken;
    }

    /**
     * 访问被阻止后，走Shiro登录的流程, 进入系统中定义的Realm执行认证流程， 调用doGetAuthenticationInfo(Token)的方法。
     * Token来自createToken的方法
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        // 执行登录操作之前，保存请求
        saveRequest(request);
        return executeLogin(request, response);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = createToken(request, response);
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
       String codeMsg = (String) httpServletRequest.getAttribute("shiroLoginFailure");
       if("code.error".equals(codeMsg)){
           SysWebUtils.writeJsonResponse(WebUtils.toHttp(response),
                   ReType.build(501, "验证码错误！"));
           return false;
         //  return onLoginFailure(token, myException, request, response);
       }

        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken "
                    + "must be created in order to execute a login attempt.";
            throw new IllegalStateException(msg);
        }
        try {
			/*if (!super.isAccessAllowed(request, response, null)) {
				throw new AuthenticationException(Constants.USER_AUTH_FAILED);
			}*/

            // 登录请求
            if (isLoginRequest(request, response)) {
                // 限定必须为Post请求
                if (!isLoginSubmission(request, response)) {
                    throw new LoginRequestMethodNotSupportException("Login Request Method Not Support " + WebUtils.toHttp(request).getMethod());
                }
            }

            Subject subject = getSubject(request, response);
            subject.login(token);

            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {



            return onLoginFailure(token, e, request, response);
        }
    }

    /**
     * 只保存请求，不去登录
     */
    @Override
//	@ApiOperation(value = "只保存请求，不去登录", notes = "request，response")
    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        saveRequest(request);
        // redirectToLogin(request, response);
    }

    /**
     * 登录成功调用事件
     */
    @Override
     protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {

        if (isLoginRequest(request, response)) {
            // 登录成功，生成Token返回, token中包含的是登录用户的id
           // AppAuthorizingRealm.Principal principal = (AppAuthorizingRealm.Principal) subject.getPrincipal();
           AppAuthenticationToken appAuthenticationToken = (AppAuthenticationToken)token;
            SysUser sysUser = userService.login(appAuthenticationToken.getUsername());
            // 生成JWT Token
          Session session = subject.getSession();
          CurrentUser currentUser = new CurrentUser(sysUser.getId(),sysUser.getUsername(),sysUser.getAge(),sysUser.getEmail(),sysUser.getPhoto(),sysUser.getRealName());
            List<SysMenu> menuList=new ArrayList<>(new HashSet<>(menuService.getUserMenu(sysUser.getId())));
            JSONArray json=menuService.getMenuJsonByUser(menuList);
            session.setAttribute("menu",json);
            CurrentMenu currentMenu=null;
            List<CurrentMenu> currentMenuList=new ArrayList<>();
            List<SysRole> roleList=new ArrayList<>();
            for(SysMenu m:menuList){
                currentMenu=new CurrentMenu(m.getId(),m.getName(),m.getPId(),m.getUrl(),m.getOrderNum(),m.getIcon(),m.getPermission(),m.getMenuType(),m.getNum());
                currentMenuList.add(currentMenu);
                roleList.addAll(m.getRoleList());
            }
            roleList= new ArrayList<>(new HashSet<>(roleList));
            List<CurrentRole> currentRoleList=new ArrayList<>();
            CurrentRole role=null;
            for(SysRole r:roleList){
                role=new CurrentRole(r.getId(),r.getRoleName(),r.getRemark());
                currentRoleList.add(role);
            }
            currentUser.setCurrentRoleList(currentRoleList);
            currentUser.setCurrentMenuList(currentMenuList);
            session.setAttribute("curentUser",currentUser);

            String accessToken = Jwts.builder().setIssuedAt(new Date()).setSubject(sysUser.getId())
                    .signWith(SignatureAlgorithm.HS512, Constants.AUTHENTICATION_ID_SECRET.getBytes()).compact();

            Map<String, String> map = new HashMap<String, String>();
            //List<Role> roleList = user.getRoleList();
            map.put("token", accessToken);

            SysWebUtils.writeJsonResponse(WebUtils.toHttp(response),new ReType(1,"登陆成功",map));

            return false;
        }

        // 获取登录前的请求
        SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
        String requestUrl=null;
        if(savedRequest!=null) {
            requestUrl = savedRequest.getRequestUrl();
        }else {
            SysWebUtils.writeJsonResponse(WebUtils.toHttp(response),new ReType(0,"登录异常，未能获取正确请求！",""));
        }

        // 登录成功后，将请求转发
        HttpServletRequest http = WebUtils.toHttp(request);


        String contextPath = WebUtils.getContextPath(http);

        // 处理拦截之前的请求Url的路径问题，不处理，导致多出一级的项目名
        if (StringUtils.startsWith(requestUrl, contextPath)) {
            requestUrl = StringUtils.substring(requestUrl, contextPath.length());
        }

        // 处理拦截之前的请求Url携带的参数。 ?号后面拼接的，如果不处理，映射到SpringMVC的Controller中的时候，参数就会重复
        if (StringUtils.indexOf(requestUrl, "?") != -1) {

            requestUrl = StringUtils.substring(requestUrl, 0, StringUtils.indexOf(requestUrl, "?"));
        }

        // 将拦截之前的请求Url转发

        http.getRequestDispatcher(requestUrl).forward(request, response);

        return false;
    }

    /**
     * 登录失败调用事件
     */
    @Override
//	@ApiOperation(value = "登录失败调用事件", notes = "request，response")
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
                                     ServletResponse response) {
        // 清除保存的请求
        WebUtils.getAndClearSavedRequest(request);

        // 请求方式不支持
        if (e instanceof LoginRequestMethodNotSupportException) {
            SysWebUtils.writeJsonResponse(WebUtils.toHttp(response),
                    ReType.build(500, e.getMessage()));
            return false;
        }

        // 账户被禁用
        if (e instanceof DisabledAccountException) {
            SysWebUtils.writeJsonResponse(WebUtils.toHttp(response),
                    ReType.build(500, e.getMessage()));
            return false;
        }

        // 账户被锁定
        if (e instanceof LockedAccountException) {
            SysWebUtils.writeJsonResponse(WebUtils.toHttp(response),
                    ReType.build(500, e.getMessage()));
            return false;
        }

        // 用户名或密码不正确
        if (e instanceof UnknownAccountException || e instanceof CredentialsException) {

            SysWebUtils.writeJsonResponse(WebUtils.toHttp(response),
                    ReType.build(500, Constants.USER_LOGIN_FAILED));
            return false;
        }

        // 无效的access_token,  Jwt解析失败
        if (e instanceof InvalidAccessTokenAuthenticationException) {
            SysWebUtils.writeJsonResponse(WebUtils.toHttp(response),
                    ReType.build(500, Constants.USER_TOKEN_FAILED));
            return false;
        }

        SysWebUtils.writeJsonResponse(WebUtils.toHttp(response),
                ReType.build(500, e.getMessage()));

        return false;
    }

    /**
     * 是否允许访问，返回false，则会执行onAccessDenied(*, *)方法
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    protected boolean isLoginSubmission(ServletRequest request, ServletResponse response) {
        return (request instanceof HttpServletRequest)
                && WebUtils.toHttp(request).getMethod().equalsIgnoreCase(POST_METHOD);
    }

    protected String getUsername(ServletRequest request) {
        return WebUtils.getCleanParam(request, getUsernameParam());
    }

    protected String getPassword(ServletRequest request) {
        return WebUtils.getCleanParam(request, getPasswordParam());
    }

    public String getUsernameParam() {
        return usernameParam;
    }

    public void setUsernameParam(String usernameParam) {
        this.usernameParam = usernameParam;
    }

    public String getPasswordParam() {
        return passwordParam;
    }

    public void setPasswordParam(String passwordParam) {
        this.passwordParam = passwordParam;
    }
}
