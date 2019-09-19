package com.capinfo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author zhuxiaomeng
 * @date 2017/12/19.
 * @email 154040976@qq.com
 * 查询返回json格式依照ui默认属性名称
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class ReType implements Serializable{
  /**状态*/
  @Expose
  public int code=0;
  /**状态信息*/
  @Expose
  public String msg="";
  /**数据总数*/
  @Expose
  public long count;
  @Expose
  public Object obj;

  @Expose
  public List<?> data;
  public ReType() {
    super();
  }

  public ReType(long count, List<?> data) {
    this.count = count;
    this.data = data;
  }
  public ReType(long count, List<?> data,int code) {
    this.count = count;
    this.data = data;
    this.code = code;
  }
  public ReType(int code,String msg,Object obj) {
    this.code = code;
    this.msg = msg;
    this.obj = obj;
  }

  public static ReType build(int code,String msg){
    return new ReType(code,msg,"");
  }

  public static ReType ok(){
    return new ReType(1,"","");
  }
  public static ReType ok(Object obj){
    return  new ReType(1,"",obj);
  }
  public static ReType fail(){
    return new ReType(0,"","");
  }
  public static ReType fail(String msg){
    return new ReType(0,msg,"");
  }
  /**
   * 动态添加属性 map 用法可以参考 activiti 模块中 com.capinfo.JsonTest 测试类中用法
   * @param count
   * @param data
   * @param map
   * @param node 绑定节点字符串 这样可以更加灵活
   * @return
   */
  public static String jsonStrng(long count,List<?> data,Map<String, Map<String,Object>> map,String node){
    JSONArray jsonArray=JSONArray.parseArray(JSON.toJSONString(data));
    JSONObject object=new JSONObject();
    for(int i=0;i<jsonArray.size();i++){
      JSONObject jsonData = (JSONObject) jsonArray.get(i);
      jsonData.putAll(map.get(jsonData.get(node)));
    }
    object.put("count",count);
    object.put("data",jsonArray);
    object.put("code",0);
    object.put("msg","");
    return object.toJSONString();
  }
}
