package com.capinfo.util;


import com.alibaba.fastjson.JSONObject;

/**
 * @author zhuxiaomeng
 * @date 2017/12/15.
 * @email 154040976@qq.com
 * ajax 回执
 */
public class JsonUtil {

  //默认成功
  private boolean flag=true;
  private String msg;
  private JSONObject josnObj;
  private Object object;
  private Integer code;

  public boolean isFlag() {
    return flag;
  }

  public void setFlag(boolean flag) {
    this.flag = flag;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public JSONObject getJosnObj() {
    return josnObj;
  }

  public void setJosnObj(JSONObject josnObj) {
    this.josnObj = josnObj;
  }

  public void setObject(Object object){
    this.object = object;
  }

  public Object getObject(){
    return object;
  }
  public void setCode(Integer code){
    this.code = code;
  }

  public Integer getCode(){
    return code;
  }
  public JsonUtil() {
  }

  public JsonUtil(boolean flag, String msg) {
    this.flag = flag;
    this.msg = msg;
  }
  public JsonUtil(Integer code, String msg,Object object) {
    this.code = code;
    this.msg = msg;
    this.object = object;
  }
  /**restful 返回*/
  public static JsonUtil error(String msg){
    return new JsonUtil(false,msg);
  }
  public  static JsonUtil sucess(String msg){
    return new JsonUtil(true,msg);
  }
  public static JsonUtil sucess(String msg,Object object){
    return new JsonUtil(1,msg,object);
  }


}
