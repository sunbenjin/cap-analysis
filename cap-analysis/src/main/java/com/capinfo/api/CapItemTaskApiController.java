package com.capinfo.api;

import com.capinfo.base.CurrentUser;
import com.capinfo.core.shiro.ShiroUtil;
import com.capinfo.entity.CapItemRequirement;
import com.capinfo.entity.CapItemTask;
import com.capinfo.exception.MyException;
import com.capinfo.service.CapItemRequirementService;
import com.capinfo.service.CapItemTaskService;
import com.capinfo.service.CapTaskPersonService;
import com.capinfo.util.CommonUtil;
import com.capinfo.util.ReType;
import com.capinfo.utils.SysConstants;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/task")
public class CapItemTaskApiController {
    @Autowired
    private CapItemTaskService itemTaskService;
    @Autowired
    private CapTaskPersonService taskPersonService;
    @Autowired
    private CapItemRequirementService itemRequirementService;


    @ApiOperation(value = "/getItemTaskList", httpMethod = "GET", notes = "任务列表")
    @GetMapping(value = "getItemTaskList")
    @ResponseBody
    public ReType getItemTaskList(CapItemTask capItemTask, String page, String limit){
        List<CapItemTask> tList = null;
        Page<CapItemTask> tPage = PageHelper.startPage(Integer.valueOf(page),Integer.valueOf(limit));
        try {
            capItemTask.setDelFlag(0);
            CurrentUser currentUser = CommonUtil.getUser();


            List<String> roleIdList = ShiroUtil.getRoleList();
            if(!CollectionUtils.isEmpty(roleIdList)){
                if(roleIdList.contains(SysConstants.ROLE_ZXTD)){
                    capItemTask.setTaskPersons(currentUser.getId());
                }
            }
            tList = itemTaskService.selectListByPage(capItemTask);
            return new ReType(tPage.getTotal(),tList);
        }catch (MyException e){
            e.printStackTrace();
            return ReType.fail("获取任务列表失败");
        }
    }
}
