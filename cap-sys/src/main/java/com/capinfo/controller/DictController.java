package com.capinfo.controller;

import com.alibaba.fastjson.JSONArray;
import com.capinfo.base.BaseController;
import com.capinfo.core.annotation.Log;
import com.capinfo.core.annotation.Log.LOG_TYPE;
import com.capinfo.entity.SysDict;
import com.capinfo.entity.SysDictItem;
import com.capinfo.entity.SysMenu;
import com.capinfo.entity.SysRoleMenu;
import com.capinfo.exception.MyException;
import com.capinfo.service.DictItemService;
import com.capinfo.service.DictService;
import com.capinfo.service.MenuService;
import com.capinfo.service.RoleMenuService;
import com.capinfo.util.BeanUtil;
import com.capinfo.util.JsonUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author zhuxiaomeng
 * @date 2017/12/13.
 * @email 154040976@qq.com
 * 菜单
 */
@RequestMapping("/dict")
@Controller
public class DictController extends BaseController {


    @Autowired
    private DictService dictService;

    @Autowired
    private DictItemService dictItemService;

    /**
     *
     * @param dictItem
     * @return
     */
    @ApiOperation(value = "/getDict", httpMethod = "GET", notes = "展示菜单")
    @GetMapping(value = "getDict")
    @ResponseBody
    public JsonUtil getDict(SysDict dictItem) {

        try {
            dictItem.setDelFlag(0);
            List<SysDict> dictList = dictService.selectListByPage(dictItem);
            return JsonUtil.sucess("获取字典成功",dictList);
        }catch (MyException e){
            return JsonUtil.error("获取字典失败");
        }


    }



}
