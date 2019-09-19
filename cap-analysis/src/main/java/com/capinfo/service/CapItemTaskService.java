package com.capinfo.service;

import com.capinfo.base.BaseService;
import com.capinfo.entity.CapItemTask;

import java.util.List;

public interface CapItemTaskService extends BaseService<CapItemTask,String> {
    int deleteByPrimaryKey(String id);

    @Override
    int insert(CapItemTask capItemTask);

    @Override
    int insertSelective(CapItemTask capItemTask);


    CapItemTask selectByPrimaryKey(String id);

    @Override
    int updateByPrimaryKeySelective(CapItemTask capItemTask);

    @Override
    int updateByPrimaryKey(CapItemTask capItemTask);

    @Override
    List<CapItemTask> selectListByPage(CapItemTask capItemTask);
}
