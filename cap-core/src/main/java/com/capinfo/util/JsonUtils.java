package com.capinfo.util;

import com.google.common.collect.Lists;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class JsonUtils {
    private static final String format = "yyyy-MM-dd HH:mm:ss"; //日期格式
    private static final Gson gson; //JSON转换器

    static {
        gson = new GsonBuilder().disableHtmlEscaping().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() { //设置Date类型转换格式

            @Override
            public Date deserialize(JsonElement json, Type typeOfT,
                                    JsonDeserializationContext context)
                    throws JsonParseException {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                String dateStr = json.getAsString();
                try {
                    return sdf.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }

        }).registerTypeHierarchyAdapter(Date.class, new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc,
                                         JsonSerializationContext context) {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return new JsonPrimitive(sdf.format(src));
            }
        }).setDateFormat(format).excludeFieldsWithoutExposeAnnotation().create();
    }

    /**
     * 将一个对象转换成JSON串
     * @param obj
     * @return
     */
    public static <T> String toJson(T obj) {
        return gson.toJson(obj);
    }

    /**
     * 将一个JSON串转换成一个对象
     * @param jsonStr
     * @param clazz
     * @return
     */
    public static <T> T fromJson(String jsonStr, Class<T> clazz){
        return gson.fromJson(jsonStr, clazz);
    }

    /**
     * 将一个对换转换成另一种类型的对象
     * @param ori 源对象
     * @param dest 目标对象的类型
     * @return
     */
    public static <T, E> T jsonBeanCopy(E ori, Class<T> dest) {
        String str = JsonUtils.toJson(ori);
        return JsonUtils.fromJson(str, dest);
    }

    /**
     * 将一个列表换转换成另一种类型的列表
     * @param ori 源列表
     * @param dest 目标列表的类型
     * @return
     */
    public static <T, E> List<T> jsonListCopy(List<E> ori, Class<T> dest) {
        List<T> list = Lists.newArrayList();
        for(E e : ori) {
            T d = jsonBeanCopy(e, dest);
            list.add(d);
        }
        return list;
    }

}
