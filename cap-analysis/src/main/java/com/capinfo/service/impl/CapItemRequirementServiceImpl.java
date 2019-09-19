package com.capinfo.service.impl;

import com.capinfo.base.BaseMapper;
import com.capinfo.base.impl.BaseServiceImpl;
import com.capinfo.entity.CapItemRequirement;
import com.capinfo.mapper.CapItemRequirementMapper;
import com.capinfo.service.CapItemRequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CapItemRequirementServiceImpl extends BaseServiceImpl<CapItemRequirement, String> implements CapItemRequirementService {

    @Autowired
    private CapItemRequirementMapper capItemRequirementMapper;

    @Override
    public BaseMapper<CapItemRequirement, String> getMappser() {
        return capItemRequirementMapper;
    }

    @Override
    public CapItemRequirement selectByPrimaryKey(String id) {
        return capItemRequirementMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return capItemRequirementMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CapItemRequirement capItemRequirement) {
        capItemRequirement = super.addValue(capItemRequirement, false);
        return capItemRequirementMapper.insert(capItemRequirement);
    }

    @Override
    public int updateByPrimaryKeySelective(CapItemRequirement capItemRequirement) {
        capItemRequirement = super.addValue(capItemRequirement, false);
        return capItemRequirementMapper.updateByPrimaryKeySelective(capItemRequirement);
    }

    @Override
    public int updateByPrimaryKey(CapItemRequirement capItemRequirement) {
        return capItemRequirementMapper.updateByPrimaryKey(capItemRequirement);
    }

    @Override
    public List<CapItemRequirement> selectListByPage(CapItemRequirement record) {
        return capItemRequirementMapper.selectListByPage(record);
    }
}
