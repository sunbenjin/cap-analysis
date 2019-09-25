package com.capinfo.api;

import com.capinfo.base.CurrentRole;
import com.capinfo.base.CurrentUser;
import com.capinfo.core.shiro.ShiroUtil;
import com.capinfo.entity.CapCheckBook;
import com.capinfo.entity.FileUrlEntity;
import com.capinfo.entity.SysDictItem;
import com.capinfo.entity.SysRole;
import com.capinfo.exception.MyException;
import com.capinfo.service.CapCheckBookService;
import com.capinfo.service.DictItemService;
import com.capinfo.util.BeanUtil;
import com.capinfo.util.CommonUtil;
import com.capinfo.util.ReType;
import com.capinfo.utils.FileinputUtils;
import com.capinfo.utils.HttpUtils;
import com.capinfo.utils.ResponseEntity;
import com.capinfo.utils.SysConstants;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/api/checkBook")
public class CapCheckBookApiController {
    @Value("${upload-path}")
    private  String uploadPath;
    @Value("${image-path}")
    private  String imagePath;
    @Value("${file-server}")
    private  String fileServer;
    @Autowired
    private CapCheckBookService checkBookService;
    @Autowired
    private DictItemService dictItemService;

    @ApiOperation(value = "/getCheckList", httpMethod = "GET", notes = "展示菜单")
    @GetMapping(value = "getCheckList")
    @ResponseBody
    public ReType getCheckList(CapCheckBook capCheckBook,String page,String limit){
        List<CapCheckBook> tList = null;
        List<CapCheckBook> pageList = new ArrayList<>();
        Page<CapCheckBook> tPage = PageHelper.startPage(Integer.valueOf(page),Integer.valueOf(limit));
        try {
            CurrentUser currentUser = (CurrentUser)ShiroUtil.getSession().getAttribute("curentUser");
           // CurrentUser currentUser = CommonUtil.getUser();
            List<String> roleIdList = ShiroUtil.getRoleList();

              if(!CollectionUtils.isEmpty(roleIdList)){
                  if(roleIdList.contains(SysConstants.ROLE_ZXTD)){
                      capCheckBook.setCheckPerson(currentUser.getId());
                      capCheckBook.setDelFlag(0);
                      tList = checkBookService.selectListByPage(capCheckBook);
                  }
                  if(!CollectionUtils.isEmpty(tList)){
                      for(CapCheckBook checkBook : tList){
                          String bookJson = HttpUtils.doGet(imagePath+"?busiId="+checkBook.getId()+"&fileUniqueCode=check_book_pic","utf-8");
                          GsonBuilder bookBuilder = new GsonBuilder();
                          Gson bookGson = bookBuilder.create();
                          ResponseEntity bookResponseEntity = bookGson.fromJson(bookJson,ResponseEntity.class);
                          if(bookResponseEntity!=null){
                              List<FileUrlEntity> bookFileUrlEntities = bookResponseEntity.getData();
                              if(!CollectionUtils.isEmpty(bookFileUrlEntities)){
                                  checkBook.setFirstPicUrl(fileServer+"/image/"+bookFileUrlEntities.get(0).getFileUrl());
                              }
                          }
                          pageList.add(checkBook);
                      }
                  }
              }else{
                return ReType.fail("请先登陆系统");
            }

            return new ReType(tPage.getTotal(),pageList);
        }catch (MyException e){
            e.printStackTrace();
            return ReType.fail("获取检查列表异常");
        }
    }
    @ApiOperation(value = "/saveCheckBook", httpMethod = "POST", notes = "展示菜单")
    @PostMapping (value = "saveCheckBook")
    @ResponseBody
    public ReType saveCheckBook(CapCheckBook capCheckBook){
        CurrentUser currentUser = (CurrentUser)ShiroUtil.getSession().getAttribute("curentUser");
        try {
           capCheckBook.setDelFlag(0);
           capCheckBook.setId(UUID.randomUUID()+"");
           capCheckBook.setCheckPerson(currentUser.getId());
           capCheckBook.setCreateBy(currentUser.getId());
           capCheckBook.setCreateDate(new Date());
           capCheckBook.setCheckDate(new Date());
           capCheckBook.setUpdateBy(currentUser.getId());
           capCheckBook.setUpdateDate(new Date());
           String dyCode = capCheckBook.getDynCode();
           checkBookService.insert(capCheckBook);
            Map<String,String> checkPic = new HashMap<>();
            Map<String,String> locationPic = new HashMap<>();
            if(!StringUtils.isEmpty(dyCode)){
                checkPic.put("filesDynCode",dyCode);
                checkPic.put("fileUniqueCode","check_book_pic");
                checkPic.put("busiId",capCheckBook.getId());
                locationPic.put("filesDynCode",dyCode);
                locationPic.put("busiId",capCheckBook.getId());
                locationPic.put("fileUniqueCode","check_location_pic");
                locationPic.put("longitude",capCheckBook.getLongitude());
                locationPic.put("latitude",capCheckBook.getLatitude());
                locationPic.put("address",capCheckBook.getProblemAddress());
                checkPic.put("longitude",capCheckBook.getLongitude());
                checkPic.put("latitude",capCheckBook.getLatitude());
                checkPic.put("address",capCheckBook.getProblemAddress());
                FileinputUtils.updateFiles(checkPic,uploadPath);
                FileinputUtils.updateFiles(locationPic,uploadPath);
            }
           return ReType.build(1,"保存检查信息成功");

        }catch (MyException e){
            return ReType.fail("保存检查异常");
        }

    }
    @ApiOperation(value = "/getCheckBook", httpMethod = "POST", notes = "查看检查详情")
    @PostMapping (value = "getCheckBook")
    @ResponseBody
    public ReType getCheckBook(CapCheckBook capCheckBook){
        CurrentUser currentUser = (CurrentUser)ShiroUtil.getSession().getAttribute("curentUser");
        try {
            if(StringUtils.isNotBlank(capCheckBook.getId())){
                capCheckBook.setCheckPerson(currentUser.getId());
                capCheckBook = checkBookService.selectByPrimaryKey(capCheckBook.getId());
             if(capCheckBook!=null){
                    String checkProblemType = capCheckBook.getCheckProblemType();
                    if(StringUtils.isNotBlank(checkProblemType)){
                        setCheckBookText(capCheckBook, checkProblemType,"checkProblemType");
                    }
                    String checkContent = capCheckBook.getCheckContent();
                    if(StringUtils.isNotBlank(checkContent)){
                        setCheckBookText(capCheckBook, checkContent,"checkContent");
                    }
                    String checkIndex = capCheckBook.getCheckIndex();
                    if(StringUtils.isNotBlank(checkIndex)){
                        setCheckBookText(capCheckBook,checkIndex,"checkIndex");
                    }
                }
                return ReType.ok(capCheckBook);
            }else{
                return ReType.fail("得到检查详情异常");
            }

        }catch (MyException e){
            return ReType.fail("得到检查详情异常");
        }

    }

    private void setCheckBookText(CapCheckBook capCheckBook, String value,String property) {
        SysDictItem sysDictItem = dictItemService.selectByPrimaryKey(value);
        if(sysDictItem!=null) {
            switch (property) {
                case "checkProblemType":
                    capCheckBook.setCheckProblemTypeText(sysDictItem.getName());
                    break;
                case "checkContent":
                    capCheckBook.setCheckContentText(sysDictItem.getName());
                    break;
                case "checkIndex":
                    capCheckBook.setCheckIndexText(sysDictItem.getName());
                    break;
            }
        }

    }

    @ApiOperation(value = "/updateCheckBook", httpMethod = "POST", notes = "展示菜单")
    @PostMapping (value = "updateCheckBook")
    @ResponseBody
    public ReType updateCheckBook(CapCheckBook capCheckBook){
        CurrentUser currentUser = (CurrentUser)ShiroUtil.getSession().getAttribute("curentUser");
        try {

            if(StringUtils.isEmpty(capCheckBook.getId())){
                return ReType.fail("修改检查信息异常");
            }
            CapCheckBook oldCheckBook = checkBookService.selectByPrimaryKey(capCheckBook.getId());
            if(oldCheckBook==null){
                return ReType.fail("修改检查信息异常");
            }
            BeanUtil.copyNotNullBean(capCheckBook,oldCheckBook);
            oldCheckBook.setUpdateBy(currentUser.getId());
            oldCheckBook.setUpdateDate(new Date());

            checkBookService.updateByPrimaryKey(oldCheckBook);
            Map<String,String> checkPic = new HashMap<>();
            Map<String,String> locationPic = new HashMap<>();
            String dyCode = capCheckBook.getDynCode();
            if(!StringUtils.isEmpty(dyCode)){
                checkPic.put("filesDynCode",dyCode);
                checkPic.put("fileUniqueCode","check_book_pic");
                checkPic.put("busiId",capCheckBook.getId());
                locationPic.put("filesDynCode",dyCode);
                locationPic.put("busiId",capCheckBook.getId());
                locationPic.put("fileUniqueCode","check_location_pic");
                locationPic.put("longitude",capCheckBook.getLongitude());
                locationPic.put("latitude",capCheckBook.getLatitude());
                locationPic.put("address",capCheckBook.getProblemAddress());
                checkPic.put("longitude",capCheckBook.getLongitude());
                checkPic.put("latitude",capCheckBook.getLatitude());
                checkPic.put("address",capCheckBook.getProblemAddress());
                FileinputUtils.updateFiles(checkPic,uploadPath);
                FileinputUtils.updateFiles(locationPic,uploadPath);
            }
            return ReType.build(1,"修改检查信息成功");

        }catch (MyException e){
            return ReType.fail("修改检查信息异常");
        }

    }
    @ApiOperation(value = "/delCheckBook", httpMethod = "POST", notes = "展示菜单")
    @PostMapping (value = "delCheckBook")
    @ResponseBody
    public ReType delCheckBook(CapCheckBook capCheckBook){
        CurrentUser currentUser = (CurrentUser)ShiroUtil.getSession().getAttribute("curentUser");
        try {

            if(StringUtils.isEmpty(capCheckBook.getId())){
                return ReType.fail("删除检查信息异常");
            }
            CapCheckBook oldCheckBook = checkBookService.selectByPrimaryKey(capCheckBook.getId());
            if(oldCheckBook==null){
                return ReType.fail("删除检查信息异常");
            }
            capCheckBook.setUpdateDate(new Date());
            capCheckBook.setUpdateBy(currentUser.getId());
            capCheckBook.setDelFlag(1);

            checkBookService.updateByPrimaryKeySelective(capCheckBook);
            return ReType.build(1,"删除检查信息成功");

        }catch (MyException e){
            return ReType.fail("保存检查异常");
        }

    }
}
