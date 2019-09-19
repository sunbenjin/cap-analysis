package com.capinfo.core.shiro;

import com.alibaba.fastjson.JSONArray;
import com.capinfo.base.CurrentMenu;
import com.capinfo.base.CurrentRole;
import com.capinfo.base.CurrentUser;
import com.capinfo.core.token.AppAuthenticationToken;
import com.capinfo.core.utils.Constants;
import com.capinfo.core.utils.Encodes;
import com.capinfo.entity.SysMenu;
import com.capinfo.entity.SysRole;
import com.capinfo.entity.SysUser;
import com.capinfo.exception.InvalidAccessTokenAuthenticationException;
import com.capinfo.service.MenuService;
import com.capinfo.service.SysUserService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AppAuthorizingRealm extends AuthenticatingRealm {
    @Autowired
    private SysUserService userService;
    @Autowired
    private MenuService menuService;
    public AppAuthorizingRealm(){
        this.setAuthenticationTokenClass(AppAuthenticationToken.class);
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        AppAuthenticationToken token = (AppAuthenticationToken)authenticationToken;
        if(StringUtils.isBlank(token.getUsername()) && StringUtils.isBlank(token.getToken())){
            throw new AuthenticationException(Constants.USER_AUTH_FAILED);
        }
        SysUser sysUser = userService.login(token.getUsername());
        String accessToken = token.getToken();
        if(StringUtils.isNotBlank(accessToken)){
            String id = "";
            try {
                id= Jwts.parser().setSigningKey(Constants.AUTHENTICATION_ID_SECRET.getBytes()).parseClaimsJws(accessToken).getBody().getSubject();
                // 包装成Shiro的AuthenticationException，交由Shiro去处理
            }catch (JwtException e){
                throw new InvalidAccessTokenAuthenticationException("无效的access_token：" + accessToken);
            }
            sysUser = userService.selectByPrimaryKey(id);
            CurrentUser currentUser = new CurrentUser(sysUser.getId(),sysUser.getUsername(),sysUser.getAge(),sysUser.getEmail(),sysUser.getPhoto(),sysUser.getRealName());
            Subject subject = ShiroUtil.getSubject();
            /**角色权限封装进去*/
            //根据用户获取菜单
            List<SysMenu> menuList=new ArrayList<>(new HashSet<>(menuService.getUserMenu(id)));
            JSONArray json=menuService.getMenuJsonByUser(menuList);
            Session session= subject.getSession();
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
        }
        if(sysUser!=null){
            if (StringUtils.isNotBlank(accessToken)) {
                return new SimpleAuthenticationInfo(sysUser, accessToken, getName());
            }
          //  byte[] salt = Encodes.decodeHex(user.getPassword().substring(0, 16));
            ByteSource byteSource=ByteSource.Util.bytes(token.getUsername());
            /*return new SimpleAuthenticationInfo(new Principal(user), user.getPassword().substring(16),
                    ByteSource.Util.bytes(salt), getName());*/
          return  new SimpleAuthenticationInfo(token.getUsername(),sysUser.getPassword(), byteSource, getName());
        }
        return null;
    }
    @Override
    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {
        CredentialsMatcher cm = getCredentialsMatcher();
        if (cm != null) {

            if (token instanceof AppAuthenticationToken) {
                if (StringUtils.isNotBlank(((AppAuthenticationToken) token).getToken())) {
                    return;
                }
            }

           if (!cm.doCredentialsMatch(token, info)) {
                //not successful - throw an exception to indicate this:
                String msg = "Submitted credentials for token [" + token + "] did not match the expected credentials.";
                throw new IncorrectCredentialsException(msg);
            }
        } else {
            throw new AuthenticationException("A CredentialsMatcher must be configured in order to verify " +
                    "credentials during authentication.  If you do not wish for credentials to be examined, you " +
                    "can configure an " + AllowAllCredentialsMatcher.class.getName() + " instance.");
        }
    }

    /**
     * 设定密码校验的Hash算法与迭代次数
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(Constants.HASH_ALGORITHM);
        matcher.setHashIterations(Constants.HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }
    /**
     * 授权用户信息
     */
    public static class Principal implements Serializable , java.security.Principal {

        private static final long serialVersionUID = 1L;

        private String id; // 编号
        private String loginName; // 登录名
        private String name; // 姓名
        private boolean mobileLogin; // 是否手机登录

//		private Map<String, Object> cacheMap;

        public Principal(SysUser user, boolean mobileLogin) {
            this.id = user.getId();
            this.loginName = user.getUsername();
            this.name = user.getRealName();
            this.mobileLogin = mobileLogin;
        }

        public Principal(SysUser user) {
            this.id = user.getId();
            this.loginName = user.getUsername();
            this.name = user.getRealName();

        }

        public String getId() {
            return id;
        }

        public String getLoginName() {
            return loginName;
        }

        public String getName() {
            return name;
        }

        public boolean isMobileLogin() {
            return mobileLogin;
        }

//		@JsonIgnore
//		public Map<String, Object> getCacheMap() {
//			if (cacheMap==null){
//				cacheMap = new HashMap<String, Object>();
//			}
//			return cacheMap;
//		}

        /**
         * 获取SESSIONID
         */
        public String getSessionid() {
            try{
                return (String) ShiroUtil.getSession().getId();
            }catch (Exception e) {
                return "";
            }
        }

        @Override
        public String toString() {
            return id;
        }

    }
}
