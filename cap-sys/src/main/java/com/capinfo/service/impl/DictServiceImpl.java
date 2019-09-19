package com.capinfo.service.impl;

import com.capinfo.base.BaseMapper;
import com.capinfo.base.impl.BaseServiceImpl;
import com.capinfo.entity.SysDict;
import com.capinfo.entity.SysRole;
import com.capinfo.mapper.SysDictMapper;
import com.capinfo.mapper.SysRoleMapper;
import com.capinfo.service.DictService;
import com.capinfo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhuxiaomeng
 * @date 2017/12/19.
 * @email 154040976@qq.com
 */
@Service
public class DictServiceImpl extends BaseServiceImpl<SysDict,String> implements DictService {

  @Autowired
  private SysDictMapper dictMapper;

  @Override
  public BaseMapper<SysDict, String> getMappser() {
    return dictMapper;
  }

  @Override
  public int deleteByPrimaryKey(String id) {
    return dictMapper.deleteByPrimaryKey(id);
  }

  @Override
  public int insert(SysDict record) {
    record=super.addValue(record,true);
    return dictMapper.insert(record);
  }

 /* @Override
  public int insertSelective(SysRole record) {
    return roleMapper.insertSelective(record);
  }*/

  @Override
  public SysDict selectByPrimaryKey(String id) {
    return dictMapper.selectByPrimaryKey(id);
  }

  @Override
  public int updateByPrimaryKeySelective(SysDict record) {
    return dictMapper.updateByPrimaryKeySelective(record);
  }

  @Override
  public int updateByPrimaryKey(SysDict record) {
    return dictMapper.updateByPrimaryKey(record);
  }

  @Override
  public List<SysDict> selectListByPage(SysDict record) {
    return dictMapper.selectListByPage(record);
  }


}
