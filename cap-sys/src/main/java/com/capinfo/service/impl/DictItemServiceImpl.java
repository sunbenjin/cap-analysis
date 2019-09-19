package com.capinfo.service.impl;

import com.capinfo.base.BaseMapper;
import com.capinfo.base.impl.BaseServiceImpl;

import com.capinfo.entity.SysDictItem;
import com.capinfo.mapper.SysDictItemMapper;
import com.capinfo.mapper.SysDictMapper;
import com.capinfo.service.DictItemService;
import com.capinfo.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhuxiaomeng
 * @date 2017/12/19.
 * @email 154040976@qq.com
 */
@Service
public class DictItemServiceImpl extends BaseServiceImpl<SysDictItem,String> implements DictItemService {

  @Autowired
  private SysDictItemMapper dictItemMapper;

  @Override
  public BaseMapper<SysDictItem, String> getMappser() {
    return dictItemMapper;
  }

  @Override
  public int deleteByPrimaryKey(String id) {
    return dictItemMapper.deleteByPrimaryKey(id);
  }

  @Override
  public int insert(SysDictItem record) {
    record=super.addValue(record,true);
    return dictItemMapper.insert(record);
  }

 /* @Override
  public int insertSelective(SysRole record) {
    return roleMapper.insertSelective(record);
  }*/

  @Override
  public SysDictItem selectByPrimaryKey(String id) {
    return dictItemMapper.selectByPrimaryKey(id);
  }

  @Override
  public int updateByPrimaryKeySelective(SysDictItem record) {
    return dictItemMapper.updateByPrimaryKeySelective(record);
  }

  @Override
  public int updateByPrimaryKey(SysDictItem record) {
    return dictItemMapper.updateByPrimaryKey(record);
  }

  @Override
  public List<SysDictItem> selectListByPage(SysDictItem record) {
    return dictItemMapper.selectListByPage(record);
  }


}
