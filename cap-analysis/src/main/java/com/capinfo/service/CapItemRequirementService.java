package com.capinfo.service;

import com.capinfo.base.BaseService;
import com.capinfo.entity.CapItemRequirement;

import java.util.List;

public interface CapItemRequirementService extends BaseService<CapItemRequirement,String> {


    int deleteByPrimaryKey(String id);

    @Override
    int insert(CapItemRequirement capItemRequirement);

    @Override
    int insertSelective(CapItemRequirement capItemRequirement);


    CapItemRequirement selectByPrimaryKey(String id);

    @Override
    int updateByPrimaryKeySelective(CapItemRequirement capItemRequirement);

    @Override
    int updateByPrimaryKey(CapItemRequirement record);

    @Override
    List<CapItemRequirement> selectListByPage(CapItemRequirement capItemRequirement);



}
