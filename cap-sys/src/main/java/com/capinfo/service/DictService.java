package com.capinfo.service;

import com.capinfo.base.BaseService;
import com.capinfo.entity.SysDict;
import com.capinfo.entity.SysRole;

import java.util.List;

/**
 * @author zhuxiaomeng
 * @date 2017/12/19.
 * @email 154040976@qq.com
 */
public interface DictService extends BaseService<SysDict,String> {


  int deleteByPrimaryKey(String id);

  @Override
  int insert(SysDict record);

  @Override
  int insertSelective(SysDict record);


  SysDict selectByPrimaryKey(String id);

  @Override
  int updateByPrimaryKeySelective(SysDict record);

  @Override
  int updateByPrimaryKey(SysDict record);

  @Override
  List<SysDict> selectListByPage(SysDict sysRole);



}
