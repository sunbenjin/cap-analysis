package com.capinfo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Table(name="cap_item_task")
@Data
@ToString
@EqualsAndHashCode
public class CapItemTask {
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    @Column(name = "item_requirement_id")
    private String itemRequirementId;

    @Column(name = "task_name")
    private String taskName;



    @Column(name = "task_check_table_name")
    private String taskCheckTableName;

    @Column(name = "task_type")
    private String taskType;

    @Column(name = "task_area")
    private String taskArea;


    @Column(name = "task_start_time")
    private Date taskStartTime;

    @Column(name = "task_end_time")
    private Date taskEndTime;

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
    private String taskPersons;

    @Transient
    private String checkIndex;

    @Column(name = "task_state")
    private Integer taskState;

    @Column(name = "item_dict")
    private String itemDict;
    /**
     * 1已完成任务
     * 0未完成任务
     */
    @Transient
    private String taskFlag;
}
