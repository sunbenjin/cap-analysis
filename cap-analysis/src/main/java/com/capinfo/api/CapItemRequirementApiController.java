package com.capinfo.api;

import com.capinfo.base.CurrentUser;
import com.capinfo.core.shiro.ShiroUtil;
import com.capinfo.entity.CapItemRequirement;
import com.capinfo.exception.MyException;
import com.capinfo.service.CapItemRequirementService;
import com.capinfo.util.ReType;
import com.capinfo.utils.SysConstants;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/requirement")
public class CapItemRequirementApiController {
    @Autowired
    private CapItemRequirementService itemRequirementService;

    @ApiOperation(value = "/getRequirementList", httpMethod = "GET", notes = "项目列表")
    @RequestMapping("getRequirementList")
    @ResponseBody
    public ReType getRequirementList(CapItemRequirement capItemRequirement,String page,String limit){
        List<CapItemRequirement> tList = null;
        Page<CapItemRequirement> tPage = PageHelper.startPage(Integer.valueOf(page),Integer.valueOf(limit));
        try {
            CurrentUser currentUser = (CurrentUser) ShiroUtil.getSession().getAttribute("curentUser");
            capItemRequirement.setDelFlag(0);
            List<String> roleIdList = ShiroUtil.getRoleList();
            if(!CollectionUtils.isEmpty(roleIdList)){
                if(roleIdList.contains(SysConstants.ROLE_ZXTD)){
                    capItemRequirement.setCheckPerson(currentUser.getId());
                }
            }

            tList = itemRequirementService.selectListByPage(capItemRequirement);
            return  new ReType(tPage.getTotal(),tList);
        }catch (MyException e){
            e.printStackTrace();
            return ReType.fail("获取项目列表异常");
        }
    }
}
