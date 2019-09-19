package com.capinfo.service.impl;

import com.capinfo.base.BaseMapper;
import com.capinfo.base.impl.BaseServiceImpl;
import com.capinfo.entity.CapItemRequirement;
import com.capinfo.entity.CapItemTask;
import com.capinfo.mapper.CapItemRequirementMapper;
import com.capinfo.mapper.CapItemTaskMapper;
import com.capinfo.service.CapItemRequirementService;
import com.capinfo.service.CapItemTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CapItemTaskServiceImpl extends BaseServiceImpl<CapItemTask, String> implements CapItemTaskService {
    @Autowired
    private CapItemTaskMapper capItemTaskMapper;

    @Override
    public BaseMapper<CapItemTask, String> getMappser() {
        return capItemTaskMapper;
    }

    @Override
    public CapItemTask selectByPrimaryKey(String id) {
        return capItemTaskMapper.selectByPrimaryKey(id);
    }
    @Override
    public int deleteByPrimaryKey(String id){
        return capItemTaskMapper.deleteByPrimaryKey(id);
    }
    @Override
    public int insert(CapItemTask capItemTask){
        capItemTask = super.addValue(capItemTask,false);
        return capItemTaskMapper.insert(capItemTask);
    }
    @Override
    public int updateByPrimaryKeySelective(CapItemTask capItemTask) {
        capItemTask = super.addValue(capItemTask, false);
        return capItemTaskMapper.updateByPrimaryKeySelective(capItemTask);
    }

    @Override
    public int updateByPrimaryKey(CapItemTask capItemTask) {
        return capItemTaskMapper.updateByPrimaryKey(capItemTask);
    }

    @Override
    public List<CapItemTask> selectListByPage(CapItemTask capItemTask) {
        return capItemTaskMapper.selectListByPage(capItemTask);
    }

}
