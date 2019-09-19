package com.capinfo.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "sys_dict")
@Data
@ToString
public class SysDict {
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    @Column(name="value")
    private String value;

    @Column(name="label")
    private String label;

    @Column(name="type")
    private String type;

    @Column(name="description")
    private String description;

    @Column(name="sort")
    private Integer sort;

    @Column(name="parent_id")
    private String parentId;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "del_flag")
    private Integer delFlag;

}
