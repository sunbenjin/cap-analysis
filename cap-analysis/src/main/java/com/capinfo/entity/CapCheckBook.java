package com.capinfo.entity;

import com.capinfo.service.DictItemService;
import com.capinfo.util.ApplicationContextUtil;
import com.capinfo.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

@Table(name="cap_check_book")
@Data
@ToString
@EqualsAndHashCode

public class CapCheckBook implements Serializable {
    private static final long serialVersionUID = 1L;
    //DictItemService dictItemService = (DictItemService)ApplicationContextUtil.getBean(DictItemService.class);
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;


    @Column(name="item_task_id")
    private String itemTaskId;

    @Column(name="item_requirement_id")
    private String itemRequirementId;
    
    @Column(name = "check_type")
    private String checkType;

    @Transient
    private String checkTypeText;



    @Column(name = "check_date")
    private Date checkDate;



    @Transient
    private String checkDateText;

    @Column(name="check_problem_type")
    private String checkProblemType;
    @Transient
    private String checkProblemTypeText;
    @Column(name="check_index")
    private  String checkIndex;
    @Transient
    private String checkIndexText;

    @Column(name="check_content")
    private String checkContent;



    @Transient
    private String checkContentText;

    @Column(name="duty_unit")
    private String dutyUnit;

    @Column(name="duty_avenue")
    private String dutyAvenue;

    @Column(name="point_location")
    private String pointLocation;

    @Column(name="problem_description")
    private String problemDescription;

    @Column(name="check_person")
    private String checkPerson;

    @Column(name="longitude")
    private String longitude;

    @Transient
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    @Transient
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @Column(name="latitude")
    private String latitude;

    @Column(name="remarks")
    private String remarks;

    @Column(name="problem_address")
    private String problemAddress;

    @Column(name="remarks1")
    private String remarks1;

    @Column(name="remarks2")
    private String remarks2;

    @Column(name="create_by")
    private String createBy;

    @Column(name="update_by")
    private String updateBy;

    @Column(name="update_date")
    private Date updateDate;

    @Column(name="create_date")
    private Date createDate;

    @Column(name="del_flag")
    private Integer delFlag;

    @Transient
    private String dynCode;

    @Transient
    private List<String> picList;

    @Transient
    private String picLocation;

    @Transient
    private String firstPicUrl;

    @Transient
    private String checkFlag;

    public String getCheckDateText() {
        if(checkDate !=null){
            checkDateText = DateUtils.formatDateTime(checkDate);
        }
        return checkDateText;
    }

    public void setCheckDateText(String checkDateText) {
        this.checkDateText = checkDateText;
    }
   /* public String getCheckIndexText() {
        if(StringUtils.isNotBlank(checkIndex)){
            SysDictItem sysDictItem = dictItemService.selectByPrimaryKey(checkIndex);
            if(sysDictItem!=null){
                checkIndexText = sysDictItem.getName();
            }
        }
        return checkIndexText;
    }

    public void setCheckIndexText(String checkIndexText) {
        this.checkIndexText = checkIndexText;
    }
    public String getCheckTypeText() {
        if(StringUtils.isNotBlank(checkType)){
            SysDictItem sysDictItem = dictItemService.selectByPrimaryKey(checkType);
            if(sysDictItem!=null){
                checkIndexText = sysDictItem.getName();
            }
        }
        return checkTypeText;
    }

    public void setCheckTypeText(String checkTypeText) {
        this.checkTypeText = checkTypeText;
    }

    public String getCheckContentText() {
        if(StringUtils.isNotBlank(checkContent)){
            SysDictItem sysDictItem = dictItemService.selectByPrimaryKey(checkContent);
            if(sysDictItem!=null){
                checkContentText = sysDictItem.getName();
            }
        }
        return checkContentText;
    }

    public void setCheckContentText(String checkContentText) {
        this.checkContentText = checkContentText;
    }*/
   public static void main(String[] args) throws  Exception{
       CapCheckBook checkBook = new CapCheckBook();
       checkBook.setCheckPerson("bis g");

       Field[] fields = checkBook.getClass().getDeclaredFields();
       for(Field field:fields){
           field.setAccessible(true);
           Method method = checkBook.getClass().getDeclaredMethod("getCheckPerson");
           System.out.println(field.getName()+(String)method.invoke(checkBook));
       }
   }
}
