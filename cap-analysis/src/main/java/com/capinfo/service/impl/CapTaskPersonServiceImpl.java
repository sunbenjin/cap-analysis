package com.capinfo.service.impl;

import com.capinfo.base.BaseMapper;
import com.capinfo.base.impl.BaseServiceImpl;
import com.capinfo.entity.CapItemTask;
import com.capinfo.entity.CapTaskPerson;
import com.capinfo.mapper.CapTaskPersonMapper;
import com.capinfo.service.CapTaskPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;
import java.util.List;
@Service
public class CapTaskPersonServiceImpl extends BaseServiceImpl<CapTaskPerson,String> implements CapTaskPersonService {
    
    @Autowired
    private CapTaskPersonMapper capTaskPersonMapper;

    @Override
    public BaseMapper<CapTaskPerson, String> getMappser() {
        return capTaskPersonMapper;
    }

    @Override
    public CapTaskPerson selectByPrimaryKey(String id) {
        return capTaskPersonMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteByPrimaryKey(String id){
        return capTaskPersonMapper.deleteByPrimaryKey(id);
    }
    @Override
    public int insert(CapTaskPerson capTaskPerson){
        capTaskPerson = super.addValue(capTaskPerson,false);
        return capTaskPersonMapper.insert(capTaskPerson);
    }
    @Override
    public int updateByPrimaryKeySelective(CapTaskPerson capTaskPerson) {
        capTaskPerson = super.addValue(capTaskPerson, false);
        return capTaskPersonMapper.updateByPrimaryKeySelective(capTaskPerson);
    }

    @Override
    public int updateByPrimaryKey(CapTaskPerson capTaskPerson) {
        return capTaskPersonMapper.updateByPrimaryKey(capTaskPerson);
    }

    @Override
    public List<CapTaskPerson> selectListByPage(CapTaskPerson capTaskPerson) {
        return capTaskPersonMapper.selectListByPage(capTaskPerson);
    }
}
