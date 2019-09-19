package com.capinfo.service.impl;

import com.capinfo.base.BaseMapper;
import com.capinfo.base.impl.BaseServiceImpl;
import com.capinfo.entity.CapCheckBook;
import com.capinfo.entity.CapCheckBook;
import com.capinfo.mapper.CapCheckBookMapper;
import com.capinfo.mapper.CapCheckBookMapper;
import com.capinfo.service.CapCheckBookService;
import com.capinfo.service.CapCheckBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CapCheckBookServiceImpl extends BaseServiceImpl<CapCheckBook, String> implements CapCheckBookService {
    @Autowired
    private CapCheckBookMapper capCheckBookMapper;

    @Override
    public BaseMapper<CapCheckBook, String> getMappser() {
        return capCheckBookMapper;
    }

    @Override
    public CapCheckBook selectByPrimaryKey(String id) {
        return capCheckBookMapper.selectByPrimaryKey(id);
    }
    @Override
    public int deleteByPrimaryKey(String id){
        return capCheckBookMapper.deleteByPrimaryKey(id);
    }
    @Override
    public int insert(CapCheckBook capItemTask){
        capItemTask = super.addValue(capItemTask,false);
        return capCheckBookMapper.insert(capItemTask);
    }
    @Override
    public int updateByPrimaryKeySelective(CapCheckBook capItemTask) {
        capItemTask = super.addValue(capItemTask, false);
        return capCheckBookMapper.updateByPrimaryKeySelective(capItemTask);
    }

    @Override
    public int updateByPrimaryKey(CapCheckBook capItemTask) {
        return capCheckBookMapper.updateByPrimaryKey(capItemTask);
    }

    @Override
    public List<CapCheckBook> selectListByPage(CapCheckBook capItemTask) {
        return capCheckBookMapper.selectListByPage(capItemTask);
    }

}
