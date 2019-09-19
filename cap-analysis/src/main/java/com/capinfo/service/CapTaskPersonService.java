package com.capinfo.service;

import com.capinfo.base.BaseService;
import com.capinfo.entity.CapTaskPerson;


import java.util.List;


public interface CapTaskPersonService extends BaseService<CapTaskPerson,String> {
    int deleteByPrimaryKey(String id);

    @Override
    int insert(CapTaskPerson capTaskPerson);

    @Override
    int insertSelective(CapTaskPerson capTaskPerson);

    CapTaskPerson selectByPrimaryKey(String id);

    @Override
    int updateByPrimaryKeySelective(CapTaskPerson capTaskPerson);

    @Override
    int updateByPrimaryKey(CapTaskPerson capTaskPerson);

    @Override
    List<CapTaskPerson> selectListByPage(CapTaskPerson capTaskPerson);
}
