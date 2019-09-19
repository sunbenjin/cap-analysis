package com.capinfo.service;

import com.capinfo.base.BaseService;
import com.capinfo.entity.CapCheckBook;

import java.util.List;

public interface CapCheckBookService extends BaseService<CapCheckBook,String> {

    int deleteByPrimaryKey(String id);

    @Override
    int insert(CapCheckBook capCheckBook);

    @Override
    int insertSelective(CapCheckBook capCheckBook);


    CapCheckBook selectByPrimaryKey(String id);

    @Override
    int updateByPrimaryKeySelective(CapCheckBook capCheckBook);

    @Override
    int updateByPrimaryKey(CapCheckBook capCheckBook);

    @Override
    List<CapCheckBook> selectListByPage(CapCheckBook capCheckBook);


}
