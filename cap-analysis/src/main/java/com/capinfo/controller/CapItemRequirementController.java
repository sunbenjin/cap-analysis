package com.capinfo.controller;


import com.capinfo.base.BaseController;
import com.capinfo.base.CurrentUser;
import com.capinfo.core.shiro.ShiroUtil;
import com.capinfo.entity.CapItemRequirement;
import com.capinfo.entity.SysRoleUser;
import com.capinfo.exception.MyException;
import com.capinfo.service.CapItemRequirementService;
import com.capinfo.service.RoleUserService;
import com.capinfo.util.*;
import com.capinfo.utils.SysConstants;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value="/capItemRequirement")
public class CapItemRequirementController extends BaseController {
   @Autowired
   private CapItemRequirementService itemRequirementService;

   @Autowired
   private RoleUserService roleUserService;
    /**
     * 项目需求列表
     * @return
     */
    @GetMapping(value="showItemList")
    @RequiresPermissions("item:view")
    public String showItemList(Model model){
        return "/item/list-item";
    }
    /**
     * 项目需求列表
     * @return
     */
    @GetMapping(value="itemRequirementList")
    @ResponseBody
    @RequiresPermissions("item:view")
    public ReType capItemRequirementList(Model model,CapItemRequirement itemRequirement,String page,String limit){
        List<CapItemRequirement> tList = null;
        Page<CapItemRequirement> tPage = PageHelper.startPage(Integer.valueOf(page), Integer.valueOf(limit));
        try {
            CurrentUser currentUser = (CurrentUser) ShiroUtil.getSession().getAttribute("curentUser");

            itemRequirement.setDelFlag(0);
            List<String> roleIdList = ShiroUtil.getRoleList();
            if(!CollectionUtils.isEmpty(roleIdList)){
                if(roleIdList.contains(SysConstants.ROLE_ZXTD)){
                    itemRequirement.setCheckPerson(currentUser.getId());
                }
            }
            tList = itemRequirementService.selectListByPage(itemRequirement);

        } catch (MyException e) {
            e.printStackTrace();
        }
        return new ReType(tPage.getTotal(),tList);


    }
    /**
     * 项目需求添加
     * @param model
     * @return
     */
    @GetMapping(value="addItem")
    public String addItemRequirement(Model model){
        return "/item/add-item";
    }
    @ApiOperation(value = "/addItem", httpMethod = "POST", notes = "添加项目类")
    @PostMapping(value = "addItem")
    @ResponseBody
    public JsonUtil addItemRequirement(CapItemRequirement itemRequirement) {
        JsonUtil j = new JsonUtil();
        String msg = "保存成功";

        try {
            itemRequirement.setId(UUID.randomUUID()+"");
            itemRequirement.setCreateDate(new Date());
            itemRequirement.setUpdateDate(new Date());
            String userId = CommonUtil.getUser().getId();
            itemRequirement.setCreateBy(userId);
            itemRequirement.setUpdateBy(userId);
            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setUserId(userId);
            List<SysRoleUser> list = roleUserService.selectByCondition(sysRoleUser);
            List<String> roleIdList = new ArrayList<>();
            if(!CollectionUtils.isEmpty(list)){
                for(int i=0; i<list.size(); i++){
                    roleIdList.add(list.get(i).getRoleId());
                }
            }
            itemRequirement.setDelFlag(0);
            if(!CollectionUtils.isEmpty(roleIdList)){
                if(roleIdList.contains(SysConstants.ROLE_WTF)){
                    //委托方
                    itemRequirement.setItemLaunchType(1);
                }else if(roleIdList.contains(SysConstants.ROLE_YJTD)){
                    //研究团队
                    itemRequirement.setItemLaunchType(2);
                }else if(roleIdList.contains(SysConstants.ROLE_ZXTD)){
                    //执行团队
                    itemRequirement.setItemLaunchType(3);
                }
            }

            itemRequirementService.insertSelective(itemRequirement);
        } catch (MyException e) {
            msg = "保存失败";
            j.setFlag(false);
            e.printStackTrace();
        }
        j.setMsg(msg);
        return j;
    }
    /**
     * 查看项目需求详情
     * @param id
     * @param model
     * @param detail
     * @return
     */
    @GetMapping(value = "updateItemDetail")
    public String updateItemDetail(String id, Model model, boolean detail) {
        if (StringUtils.isNotEmpty(id)) {
            CapItemRequirement itemRequirement = itemRequirementService.selectByPrimaryKey(id);
            model.addAttribute("itemRequirement",itemRequirement);
        }

        model.addAttribute("detail", detail+"");
        return "/item/edit-item";
    }
    @ApiOperation(value = "/updateItemDetail" ,  httpMethod = "POST", notes = "添加任务")
    @PostMapping("updateItemDetail")
    @ResponseBody
    public JsonUtil saveItemRequirement(CapItemRequirement itemRequirement){
        JsonUtil j = new JsonUtil();
        try {
            CapItemRequirement oldItemRequirement = itemRequirementService.selectByPrimaryKey(itemRequirement.getId());
            BeanUtil.copyNotNullBean(itemRequirement,oldItemRequirement);
            oldItemRequirement.setUpdateDate(new Date());
            String userId = CommonUtil.getUser().getId();
            oldItemRequirement.setUpdateBy(userId);
            itemRequirementService.updateByPrimaryKey(oldItemRequirement);
            j.setMsg("保存成功");
            j.setFlag(true);
        }catch (Exception e){
            j.setMsg("保存失败");
            j.setFlag(false);
            e.printStackTrace();

        }
        return j;
    }
    @ApiOperation(value = "/deleteItem" ,  httpMethod = "POST", notes = "更新任务")
    @PostMapping("deleteItem")
    @ResponseBody
    public JsonUtil deleteItem(String id){
        JsonUtil j = new JsonUtil();
        try {
            CapItemRequirement oldItemRequirement = itemRequirementService.selectByPrimaryKey(id);
            if(oldItemRequirement!=null){
                String userId = CommonUtil.getUser().getId();
                oldItemRequirement.setDelFlag(1);
                oldItemRequirement.setUpdateBy(userId);
                itemRequirementService.updateByPrimaryKey(oldItemRequirement);
                j.setMsg("删除成功");
                j.setFlag(true);
            }

        }catch (Exception e){
            j.setMsg("删除失败");
            j.setFlag(false);
            e.printStackTrace();

        }
        return j;
    }
}
