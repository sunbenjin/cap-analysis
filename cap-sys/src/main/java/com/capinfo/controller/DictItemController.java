package com.capinfo.controller;

import com.capinfo.base.BaseController;
import com.capinfo.entity.SysDict;
import com.capinfo.entity.SysDictItem;
import com.capinfo.exception.MyException;
import com.capinfo.service.DictItemService;
import com.capinfo.service.DictService;
import com.capinfo.util.JsonUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author zhuxiaomeng
 * @date 2017/12/13.
 * @email 154040976@qq.com
 * 菜单
 */
@RequestMapping("/dictItem")
@Controller
public class DictItemController extends BaseController {


    @Autowired
    private DictService dictService;

    @Autowired
    private DictItemService dictItemService;

    /**
     * @param parentId
     * @return
     */
    @ApiOperation(value = "/getDictItem", httpMethod = "GET", notes = "展示菜单")
    @GetMapping(value = "getDictItem")
    @ResponseBody
    public JsonUtil getDict(String parentId) {

        try {

            if (StringUtils.isNotBlank(parentId)) {
                SysDictItem dictItem = dictItemService.selectByPrimaryKey(parentId);
                return JsonUtil.sucess("获取字典成功", dictItem);
            } else {
                return JsonUtil.error("获取字典失败");
            }


        } catch (MyException e) {
            return JsonUtil.error("获取字典失败");
        }

    }

}
