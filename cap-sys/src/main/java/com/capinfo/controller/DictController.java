package com.capinfo.controller;

import com.alibaba.fastjson.JSONArray;
import com.capinfo.base.BaseController;
import com.capinfo.base.CurrentUser;
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
import com.capinfo.util.CommonUtil;
import com.capinfo.util.JsonUtil;
import com.capinfo.util.ReType;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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

import javax.jws.WebParam;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    @GetMapping("showDictList")
    public String dictList(Model model){
        return "system/dict/list-dict";
    }
    @GetMapping("dictList")
    @ResponseBody
    @RequiresPermissions("dict:view")
    public ReType dictList(Model model,SysDict sysDict,String page,String limit){
        List<SysDict> tList = null;
        Page<SysDict> tPage = PageHelper.startPage(Integer.valueOf(page),Integer.valueOf(limit));
        try {
           sysDict.setDelFlag(0);
           tList = dictService.selectListByPage(sysDict);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ReType(tPage.getTotal(),tList);
    }
    @GetMapping("addDict")
    public String addDict(Model model,SysDict sysDict){
        model.addAttribute("itemDict",sysDict);
        return "system/dict/add-dict";
    }
    @ApiOperation(value = "/addDict", httpMethod = "POST", notes = "添加字典")
    @PostMapping("addDict")
    @ResponseBody
    public JsonUtil addDict(SysDict sysDict){
        JsonUtil j = new JsonUtil();
        String msg = "保存成功";
        try {
            sysDict.setId(UUID.randomUUID()+"");
            String userId = CommonUtil.getUser().getId();
            sysDict.setCreateBy(userId);
            sysDict.setUpdateBy(userId);
            sysDict.setUpdateDate(new Date());
            sysDict.setUpdateDate(new Date());
            sysDict.setDelFlag(0);
            dictService.insertSelective(sysDict);
        } catch (Exception e) {
            e.printStackTrace();
        }

        j.setMsg(msg);
        return j;
    }

    @GetMapping("updateDict")
    @RequiresPermissions("dict:edit")
    public String getUpdateDict(String id,String detail,Model model){
        SysDict sysDict = dictService.selectByPrimaryKey(id);
        model.addAttribute("itemDict",sysDict);
        model.addAttribute("detail",detail);
        return "system/dict/edit-dict";
    }
    @PostMapping("updateDict")
    @RequiresPermissions("dict:edit")
    @ResponseBody
    public JsonUtil PostUpdateDict(SysDict sysDict){

        JsonUtil j = new JsonUtil();
        try {
            SysDict oldDict = dictService.selectByPrimaryKey(sysDict.getId());
            String userId = CommonUtil.getUser().getId();
            sysDict.setDelFlag(0);
            BeanUtil.copyNotNullBean(sysDict,oldDict);
            oldDict.setUpdateBy(userId);
            oldDict.setUpdateDate(new Date());
            dictService.updateByPrimaryKey(oldDict);
            j.setMsg("保存成功");
            j.setFlag(true);
        }catch (MyException e){
            j.setMsg("保存失败");
            j.setFlag(false);
            e.printStackTrace();
        }
        return j;
    }
    @PostMapping("deleteDict")
    @ResponseBody
    public ReType deleteDict(SysDict sysDict){
        String userId = CommonUtil.getUser().getId();
        try {
            sysDict.setDelFlag(1);
            sysDict.setUpdateDate(new Date());
            sysDict.setUpdateBy(userId);
            dictService.updateByPrimaryKeySelective(sysDict);
            return ReType.build(1,"删除检查信息成功");
        } catch (Exception e) {
            return ReType.fail("删除字典异常");
        }
    }
}
