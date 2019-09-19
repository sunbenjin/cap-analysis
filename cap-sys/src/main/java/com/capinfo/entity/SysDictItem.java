package com.capinfo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sys_dict_item")
@Data
@ToString
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class SysDictItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id")
    private String id;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "parent_ids")
    private String parentIds;

    @Column(name = "name")
    private String name;

    @Column(name = "sort")
    private Integer sort;

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
