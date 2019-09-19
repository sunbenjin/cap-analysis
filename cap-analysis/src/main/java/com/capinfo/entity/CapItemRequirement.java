package com.capinfo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * 项目需求表
 */
@Table(name="cap_item_requirement")
@Data
@ToString
@EqualsAndHashCode
public class CapItemRequirement {
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    @Column(name = "item_title")
    private String itemTitle;

    @Column(name = "begin_date")
    private Date beginDate;

    @Column(name = "item_launch_type")
    private Integer itemLaunchType;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "item_area")
    private String itemArea;

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

    @Transient
    private String checkPerson;
    /**
     * 1已完成任务
     * 0未完成任务
     */
    @Transient
    private String itemFlag;
}
