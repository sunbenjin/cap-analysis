package com.capinfo.controller;

import com.capinfo.base.BaseController;
import com.capinfo.entity.CapTaskPerson;
import com.capinfo.exception.MyException;
import com.capinfo.service.CapTaskPersonService;
import com.capinfo.util.JsonUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/capItemTask")
public class CapTaskPersonController extends BaseController {

    @Autowired
    private CapTaskPersonService taskPersonService;

    @ApiOperation(value = "/getTaskPersons", httpMethod = "GET", notes = "展示菜单")
    @GetMapping(value = "getTaskPersons")
    @ResponseBody
    public JsonUtil getTaskPersons(CapTaskPerson taskPerson){
        try {
            taskPerson.setDelFlag(0);
            List<CapTaskPerson> taskPersonZZTD = taskPersonService.selectListByPage(taskPerson);
            return JsonUtil.sucess("获取人员列表成功",taskPersonZZTD);
        }catch (MyException e){
            return JsonUtil.error("获取人员列表异常");
        }
    }
}
