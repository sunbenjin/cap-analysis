package com.capinfo.api;

import com.capinfo.base.CurrentUser;
import com.capinfo.entity.SysDictItem;
import com.capinfo.exception.MyException;
import com.capinfo.service.DictItemService;
import com.capinfo.util.CommonUtil;
import com.capinfo.util.JsonUtil;
import com.capinfo.utils.FileUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

@Controller
@RequestMapping("/api/common")
public class CommonController {
    @Autowired
    private DictItemService dictItemService;

    @ApiOperation(value = "/getDict", httpMethod = "GET", notes = "展示菜单")
    @GetMapping(value = "getDict")
    @ResponseBody
    public JsonUtil getDict(SysDictItem dictItem) {

        try {
            dictItem.setDelFlag(0);
            List<SysDictItem> dictList = dictItemService.selectListByPage(dictItem);
            return JsonUtil.sucess("获取字典成功",dictList);
        }catch (MyException e){
            return JsonUtil.error("获取字典失败");
        }


    }
    @ApiOperation(value = "/getUserInfo", httpMethod = "GET", notes = "展示菜单")
    @GetMapping(value = "getUserInfo")
    @ResponseBody
    public JsonUtil getUserInfo() {

        try {
            CurrentUser currentUser = CommonUtil.getUser();

            return JsonUtil.sucess("获取个人消息成功",currentUser);
        }catch (MyException e){
            return JsonUtil.error("获取个人消息失败");
        }
    }


}
