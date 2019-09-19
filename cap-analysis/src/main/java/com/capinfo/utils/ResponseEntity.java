package com.capinfo.utils;

import com.capinfo.entity.FileUrlEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class ResponseEntity implements Serializable {
    private static final long serialVersionUID=1L;

    private boolean flag;
    private Integer code;
    private String msg;
    private List<FileUrlEntity> data;



}
