package com.capinfo.controller;

import com.capinfo.core.shiro.ShiroUtil;
import com.capinfo.entity.*;
import com.capinfo.exception.MyException;
import com.capinfo.service.*;
import com.capinfo.util.CommonUtil;
import com.capinfo.util.DateUtils;
import com.capinfo.util.ReType;
import com.capinfo.utils.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.List;

@Controller
@RequestMapping("/check")
public class CapCheckBookController {
    @Autowired
    private CapCheckBookService checkBookService;
    @Autowired
    private CapItemTaskService itemTaskService;
    @Autowired
    private CapItemRequirementService itemRequirementService;
    @Autowired
    private CapTaskPersonService taskPersonService;
    @Autowired
    private DictItemService dictItemService;

    @Value("${uploader.basePath}")
    private String basePath;

    @Value("${image-path}")
    private String imagePath;
    @GetMapping("showTaskList")
    public String itemTaskList(Model model){
        return "check/list-task-check";
    }

    /**
     * 项目需求列表
     * @return
     */
    @GetMapping(value="taskCheckList")
    @ResponseBody
    @RequiresPermissions("check:show")
    public ReType itemTaskList(Model model, CapItemTask itemTask, String page, String limit){
        List<CapItemTask> tList = null;
        Page<CapItemTask> tPage = PageHelper.startPage(Integer.valueOf(page), Integer.valueOf(limit));
        try {
            String userId = CommonUtil.getUser().getId();
            itemTask.setTaskPersons(userId);
            itemTask.setDelFlag(0);
            tList = itemTaskService.selectListByPage(itemTask);
        } catch (MyException e) {
            e.printStackTrace();
        }
        return new ReType(tPage.getTotal(),tList);
    }
    /**
     * 查看子任务详情
     * @param id
     * @param model
     * @param detail
     * @return
     */
    @GetMapping(value = "listCheckDetail")
    public String editTaskDetail(String id, Model model, boolean detail) {
        if (StringUtils.isNotEmpty(id)) {
            CapItemTask itemTask = itemTaskService.selectByPrimaryKey(id);

            model.addAttribute("itemTask",itemTask);
        }
        model.addAttribute("detail", detail+"");
        return "/check/list-check-detail";
    }
    @GetMapping("showCheckList")
    public String showCheckList(Model model, HttpServletRequest request){
        String itemTaskId = request.getParameter("itemTaskId");
        model.addAttribute("itemTaskId",itemTaskId);
        String taskType = request.getParameter("taskType");
        model.addAttribute("checkType",taskType);
        return "check/list-check";
    }
    /**
     * 检查列表
     * @return
     */
    @GetMapping(value="listCheckList")
    @ResponseBody
    @RequiresPermissions("check:show")
    public ReType listCheckList(Model model, CapCheckBook checkBook, String page, String limit){
        List<CapCheckBook> tList = null;
        Page<CapCheckBook> tPage = PageHelper.startPage(Integer.valueOf(page), Integer.valueOf(limit));
        try {
            String userId = CommonUtil.getUser().getId();
            List<String> roleIdList = ShiroUtil.getRoleList();
            if(roleIdList.contains(SysConstants.ROLE_ZXTD)){
                checkBook.setCheckPerson(userId);
            }

            checkBook.setDelFlag(0);
            tList = checkBookService.selectListByPage(checkBook);
        } catch (MyException e) {
            e.printStackTrace();
        }
        return new ReType(tPage.getTotal(),tList);
    }

    /**
     * excel导出
     * @param capCheckBook
     * @param response
     * @param request
     */
    @RequestMapping(value="exportExcel",method = RequestMethod.GET)
     public void exportExcel(CapCheckBook capCheckBook, HttpServletResponse response,HttpServletRequest request){
        try {
            capCheckBook.setDelFlag(0);

            InputStream inputStream = null;
            List<CapCheckBook> list = checkBookService.selectListByPage(capCheckBook);
            String checkType = capCheckBook.getCheckType();
            String title = "";

            if(StringUtils.equals("1",checkType)){
                title  = "创城检查";
                inputStream =   this.getClass().getResourceAsStream("/excel_template/ccjc_template.xlsx");
            }else if(StringUtils.equals("2",checkType)){
                title = "环境检查";
                inputStream = this.getClass().getResourceAsStream("/excel_template/hjjc_template.xlsx");
            }else if(StringUtils.equals("3",checkType)){
                title = "自由巡检";
                inputStream = this.getClass().getResourceAsStream("/excel_template/zyxj_template.xlsx");
            }
            Context context = new Context();
            context.putVar("title",title);
            // excelBean.put("title","创城检查");
            context.putVar("exportDate", DateUtils.formatDateTime(new Date()));
            //excelBean.put("exportDate", DateUtils.formatDateTime(new Date()));

            List<Map<String,Object>> dataList = new ArrayList<>();

            if(!CollectionUtils.isEmpty(list)){

                //List<CapCheckBookExcelCCJC> excelList = new ArrayList<>();
                for(int i=0; i<list.size(); i++){
                    int index = i+1;
                    CapCheckBook checkBook = list.get(i);
                    String bookJson = HttpUtils.doGet(imagePath+"?busiId="+checkBook.getId()+"&fileUniqueCode=check_book_pic","utf-8");
                    String locationJson = HttpUtils.doGet(imagePath+"?busiId="+checkBook.getId()+"&fileUniqueCode=check_location_pic","utf-8");
                    GsonBuilder bookBuilder = new GsonBuilder();
                    GsonBuilder locationBuilder = new GsonBuilder();
                    Gson bookGson = bookBuilder.create();
                    Gson locationGson = locationBuilder.create();
                    ResponseEntity bookResponseEntity = bookGson.fromJson(bookJson,ResponseEntity.class);
                    ResponseEntity locationResponseEntity = locationGson.fromJson(locationJson,ResponseEntity.class);
                    List<FileUrlEntity> bookFileUrlEntities = bookResponseEntity.getData();
                    List<FileUrlEntity> locationFileUrlEntities = locationResponseEntity.getData();
                    Map<String,Object> map = new HashMap<>();
                    map.put("show","1");
                    map.put("index",index);
                    if(locationFileUrlEntities!=null && locationFileUrlEntities.size()>0){
                        FileUrlEntity fileUrlEntity = locationFileUrlEntities.get(0);
                        File file = new File(basePath+fileUrlEntity.getFileUrl());
                        if(file.exists()){
                            byte[] bytes = ImageUtils.getImageBytes(file);
                            map.put("image9",bytes);
                        }else{
                            map.put("flag"+9,"0");
                        }
                    }
                    Field[] fields = checkBook.getClass().getDeclaredFields();
                    for(Field field:fields){
                        field.setAccessible(true);
                        String fieldName = field.getName();
                        if(!StringUtils.equals("serialVersionUID",fieldName)){
                           map.put(field.getName(),ReflectUtils.getObjectValue(fieldName,checkBook,field));
                        }
                    }
                    if(!CollectionUtils.isEmpty(bookFileUrlEntities)){
                        for(int j=0; j<bookFileUrlEntities.size(); j++){
                            FileUrlEntity fileUrlEntity = bookFileUrlEntities.get(j);
                            File file = new File(basePath+fileUrlEntity.getFileUrl());
                            if(file.exists()){
                                byte[] bytes = ImageUtils.getImageBytes(file);
                                map.put("image"+j,bytes);
                                map.put("flag"+j,"1");
                            }else{
                                //map.put("image"+j,bytes0);
                                map.put("flag"+j,"0");
                            }
                        }
                        for(int k=bookFileUrlEntities.size(); k<9; k++){
                            //map.put("image"+k,bytes0);
                            map.put("flag"+k,"0");
                        }
                    }else{
                        for(int k=0; k<9;k++){
                            map.put("flag"+k,"0");
                           // map.put("image"+k,bytes0);
                        }
                    }
                   // excelBean.put("")
                    dataList.add(map);

                }
                //excelBean.put("dataList",dataList);
               /*context.putVar("dataList",dataList);*/



            }else{
                Map<String,Object> map = new HashMap<>();
                CapCheckBook checkBook = new CapCheckBook();
                Field[] fields = checkBook.getClass().getDeclaredFields();
                for(Field field:fields){
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    if(!StringUtils.equals("serialVersionUID",fieldName)){
                        map.put(field.getName(),"");
                    }
                }
                map.put("show","1");
                map.put("flag"+9,"0");
                for(int k=0; k<9;k++){
                    map.put("flag"+k,"0");
                }


            }
            context.putVar("dataList",dataList);
            String path = request.getSession().getServletContext().getRealPath("/")+title+"台账.xlsx";
            OutputStream outputStream = new FileOutputStream(path);
            JxlsHelper.getInstance().processTemplate(inputStream,outputStream,context);
            FileUtils.downFile(new File(path),request,response);
        }catch (Exception e){
            e.printStackTrace();

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
}
