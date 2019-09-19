package com.capinfo.controller;


import com.capinfo.base.BaseController;
import com.capinfo.entity.CapItemRequirement;
import com.capinfo.entity.CapItemTask;
import com.capinfo.entity.CapTaskPerson;
import com.capinfo.exception.MyException;
import com.capinfo.service.CapItemRequirementService;
import com.capinfo.service.CapItemTaskService;
import com.capinfo.service.CapTaskPersonService;
import com.capinfo.util.BeanUtil;
import com.capinfo.util.CommonUtil;
import com.capinfo.util.JsonUtil;
import com.capinfo.util.ReType;
import com.capinfo.utils.SysConstants;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hazelcast.util.UuidUtil;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "/capItemTask")
public class CapItemTaskController extends BaseController {

    @Autowired
    private CapItemTaskService itemTaskService;

    @Autowired
    private CapItemRequirementService itemRequirementService;

    @Autowired
    private CapTaskPersonService taskPersonService;

    @GetMapping("showItemTask")
    public String showItemTask(Model model, HttpServletRequest request){
       String itemRquirementId =  request.getParameter("itemRequirementId");
        model.addAttribute("itemRequirementId",itemRquirementId);
        return "task/list-task";
    }

    /**
     * 项目需求列表
     * @return
     */
    @GetMapping(value="itemTaskList")
    @ResponseBody
    @RequiresPermissions("item:view")
    public ReType itemRequirementByTask(CapItemRequirement itemRequirement, String page, String limit){
        List<CapItemRequirement> tList = null;
        Page<CapItemRequirement> tPage = PageHelper.startPage(Integer.valueOf(page), Integer.valueOf(limit));
        try {
            itemRequirement.setDelFlag(0);
            tList = itemRequirementService.selectListByPage(itemRequirement);

        } catch (MyException e) {
            e.printStackTrace();
        }
        return new ReType(tPage.getTotal(),tList);
    }

    /**
     * 根据项目需求id查出所有的任务
     * @param id
     * @param model
     * @param detail
     * @return
     */
    @GetMapping(value="updateTaskDetail")
    public String updateTaskDetail(String id,Model model,boolean detail){
        if(StringUtils.isNoneEmpty(id)){
            CapItemTask itemTask = itemTaskService.selectByPrimaryKey(id);
            if(itemTask!=null){
                CapTaskPerson taskPerson = new CapTaskPerson();
                taskPerson.setItemRequirementId(itemTask.getItemRequirementId());
                taskPerson.setItemTaskId(itemTask.getId());
                List<CapTaskPerson> personList = taskPersonService.selectListByPage(taskPerson);
                if(!CollectionUtils.isEmpty(personList)){
                    StringBuffer sb = new StringBuffer();
                    for(CapTaskPerson person: personList){
                        sb.append(person.getPersonId()+",");
                    }
                    if(StringUtils.isNoneEmpty(sb)){
                        String sbStr = sb.toString();
                        String taskPersonStr = sbStr.substring(0,sbStr.length()-1);
                        itemTask.setTaskPersons(taskPersonStr);
                    }
                }
            }
            model.addAttribute("itemTask",itemTask);
        }
        model.addAttribute("detail",detail+"");
        return "/task/edit-task";
    }
    /**
     * 任务列表
     * @return
     */
    @GetMapping(value="taskList")
    @ResponseBody
    @RequiresPermissions("item:view")
    public ReType itemTaskList(Model model, CapItemTask capItemTask, String page, String limit){
        List<CapItemTask> tList = null;
        Page<CapItemTask> tPage = PageHelper.startPage(Integer.valueOf(page), Integer.valueOf(limit));
        try {
            capItemTask.setDelFlag(0);
            tList = itemTaskService.selectListByPage(capItemTask);
        } catch (MyException e) {
            e.printStackTrace();
        }
        return new ReType(tPage.getTotal(),tList);
    }

    @GetMapping("addTask")
    public String addTask(Model model,String itemRequirementId){
        model.addAttribute("itemRequirementId",itemRequirementId);
        return "task/add-task";
    }
    /**
     * 添加任务
     * @param itemTask
     * @return
     */
    @ApiOperation(value = "saveTask" ,  httpMethod = "POST", notes = "添加任务")
    @PostMapping("saveTask")
    @ResponseBody
    public JsonUtil saveItemTask(CapItemTask itemTask){
        JsonUtil j = new JsonUtil();
        try {
            String userId = CommonUtil.getUser().getId();
           itemTask.setId(UUID.randomUUID()+"");
           itemTask.setCreateDate(new Date());
           itemTask.setCreateBy(userId);
           itemTask.setUpdateBy(userId);
           itemTask.setDelFlag(0);
           String taskPersons = itemTask.getTaskPersons();
           String checkTableName = SysConstants.createCheckTableName("cap_task_check");
           itemTask.setTaskCheckTableName(checkTableName);
           itemTaskService.insert(itemTask);

           if(StringUtils.isNoneEmpty(taskPersons)){
               String[] person = taskPersons.split(",");
               insertTaskPersons(itemTask, userId, person);
           }
           j.setMsg("保存成功");
           j.setFlag(true);
        }catch (Exception e){
            j.setMsg("保存失败");
            j.setFlag(false);
            e.printStackTrace();
        }
        return j;
    }
    /**
     * 查看子任务详情
     * @param id
     * @param model
     * @param detail
     * @return
     */
    @GetMapping(value = "editTaskDetail")
    public String editTaskDetail(String id, Model model, boolean detail) {
        if (StringUtils.isNotEmpty(id)) {
           CapItemTask itemTask = itemTaskService.selectByPrimaryKey(id);
           if(itemTask!=null){
               CapTaskPerson taskPerson = new CapTaskPerson();
               taskPerson.setItemRequirementId(itemTask.getItemRequirementId());
               taskPerson.setItemTaskId(itemTask.getId());
               List<CapTaskPerson> personList = taskPersonService.selectListByPage(taskPerson);
               if(!CollectionUtils.isEmpty(personList)){
                   StringBuffer sb = new StringBuffer();
                   for(CapTaskPerson person: personList){
                       sb.append(person.getPersonId()+",");
                   }
                   if(StringUtils.isNoneEmpty(sb)){
                       String sbStr = sb.toString();
                       String taskPersonStr = sbStr.substring(0,sbStr.length()-1);
                       itemTask.setTaskPersons(taskPersonStr);
                   }
               }
           }
            model.addAttribute("itemTask",itemTask);
        }
        model.addAttribute("detail", detail+"");
        return "/task/edit-task";
    }
    /**
     * 添加任务
     * @param itemTask
     * @return
     */
    @ApiOperation(value = "editTaskDetail" ,  httpMethod = "POST", notes = "更新任务")
    @PostMapping("editTaskDetail")
    @ResponseBody
    public JsonUtil editTaskDetail(CapItemTask itemTask){
        JsonUtil j = new JsonUtil();
        try {
            CapItemTask oldItemTask = itemTaskService.selectByPrimaryKey(itemTask.getId());
            String userId = CommonUtil.getUser().getId();
            itemTask.setDelFlag(0);
            String taskPersons = itemTask.getTaskPersons();
            BeanUtil.copyNotNullBean(itemTask,oldItemTask);
            oldItemTask.setUpdateBy(userId);
            oldItemTask.setUpdateDate(new Date());
            itemTaskService.updateByPrimaryKey(oldItemTask);
            if(StringUtils.isNoneEmpty(taskPersons)){
                CapTaskPerson oldTaskPerson = new CapTaskPerson();
                oldTaskPerson.setItemTaskId(itemTask.getId());
                oldTaskPerson.setItemRequirementId(itemTask.getItemRequirementId());
                List<CapTaskPerson> personList = taskPersonService.selectListByPage(oldTaskPerson);
                if(!CollectionUtils.isEmpty(personList)){
                    for(CapTaskPerson person: personList){
                        person.setDelFlag(1);
                        taskPersonService.updateByPrimaryKey(person);
                    }
                }
                String[] person = taskPersons.split(",");
                insertTaskPersons(itemTask, userId, person);
            }
            j.setMsg("保存成功");
            j.setFlag(true);
        }catch (Exception e){
            j.setMsg("保存失败");
            j.setFlag(false);
            e.printStackTrace();
        }
        return j;
    }

    private void insertTaskPersons(CapItemTask itemTask, String userId, String[] person) {
        for (String personId : person) {
            CapTaskPerson taskPerson = new CapTaskPerson();
            taskPerson.setCreateBy(userId);
            taskPerson.setPersonId(personId);
            taskPerson.setId(UUID.randomUUID() + "");
            taskPerson.setDelFlag(0);
            taskPerson.setCreateDate(new Date());
            taskPerson.setUpdateBy(userId);
            taskPerson.setUpdateDate(new Date());
            taskPerson.setItemTaskId(itemTask.getId());
            taskPerson.setItemRequirementId(itemTask.getItemRequirementId());
            taskPersonService.insert(taskPerson);
        }
    }
}
