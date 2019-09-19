package com.capinfo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ResultBean implements Serializable {

    public int code=0;
    /**状态信息*/
    public String msg="";
    /**数据总数*/
    public long count;

    public List<?> obj;

    public ResultBean(long count, List<?> obj) {
        this.count = count;
        this.obj = obj;
    }
    public ResultBean(long count, List<?> obj,int code) {
        this.count = count;
        this.obj = obj;
        this.code = code;
    }

    /**
     * 动态添加属性 map 用法可以参考 activiti 模块中 com.capinfo.JsonTest 测试类中用法
     * @param count
     * @param data
     * @param map
     * @param node 绑定节点字符串 这样可以更加灵活
     * @return
     */
    public static String jsonStrng(long count, List<?> data, Map<String, Map<String,Object>> map, String node){
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
