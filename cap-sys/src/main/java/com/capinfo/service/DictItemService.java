package com.capinfo.service;

import com.capinfo.base.BaseService;
import com.capinfo.entity.SysDict;
import com.capinfo.entity.SysDictItem;

import java.util.List;

/**
 * @author zhuxiaomeng
 * @date 2017/12/19.
 * @email 154040976@qq.com
 */
public interface DictItemService extends BaseService<SysDictItem,String> {


  int deleteByPrimaryKey(String id);

  @Override
  int insert(SysDictItem record);

  @Override
  int insertSelective(SysDictItem record);


  SysDictItem selectByPrimaryKey(String id);

  @Override
  int updateByPrimaryKeySelective(SysDictItem record);

  @Override
  int updateByPrimaryKey(SysDictItem record);

  @Override
  List<SysDictItem> selectListByPage(SysDictItem sysRole);



}
