package com.capinfo.core.shiro;

import com.capinfo.base.CurrentRole;
import com.capinfo.base.CurrentUser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuxiaomeng
 * @date 2017/12/28.
 * @email 154040976@qq.com
 */
public class ShiroUtil {

    public static Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    public static Session getSession(){
        return getSubject().getSession();
    }
    public static CurrentUser getCurrentUse(){
        return (CurrentUser) getSession().getAttribute("curentUser");
    }
    public static List<String> getRoleList(){
        CurrentUser currentUser = getCurrentUse();
        List<CurrentRole> roleList = currentUser.getCurrentRoleList();
        List<String> roleIdList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(roleList)){
            for(CurrentRole role:roleList){
                roleIdList.add(role.getId());
            }
        }
        return roleIdList;
    }

}
