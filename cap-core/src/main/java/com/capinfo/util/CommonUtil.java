package com.capinfo.util;


import com.capinfo.base.CurrentRole;
import com.capinfo.base.CurrentUser;
/*import com.capinfo.entity.SysRoleUser;
import com.capinfo.entity.SysUser;
import com.capinfo.service.SysUserService;*/
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.util.ThreadContext;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zhuxiaomeng
 * @date 2017/12/4.
 * @email 154040976@qq.com
 *
 * 管理工具类
 */
public class CommonUtil {
// private static SysUserService sysUserService = SpringUtil.getBean(SysUserService.class);
  /**
   * 获取当前用户
   */
  public static CurrentUser getUser() {

      org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
      Session session = subject.getSession();

      return (CurrentUser) session.getAttribute("curentUser");

  }

}