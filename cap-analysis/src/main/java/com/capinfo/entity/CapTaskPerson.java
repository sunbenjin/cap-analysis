package com.capinfo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name="cap_task_person")
@Data
@ToString
@EqualsAndHashCode
public class CapTaskPerson {
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

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

    @Column(name="item_task_id")
    private String itemTaskId;

    @Column(name="person_id")
    private String personId;

    @Column(name="item_requirement_id")
    private String itemRequirementId;

}
