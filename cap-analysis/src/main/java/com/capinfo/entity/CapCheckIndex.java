package com.capinfo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name="cap_check_index")
@Data
@ToString
@EqualsAndHashCode
public class CapCheckIndex {
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    @Column(name = "item_task_id")
    private String itemTaskId;

    @Column(name = "index_property")
    private String indexProperty;

    @Column(name="index_type")
    private String indexType;

    @Column(name="index_name")
    private  String indexName;

    @Column(name="index_valid")
    private Integer indexValid;

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
