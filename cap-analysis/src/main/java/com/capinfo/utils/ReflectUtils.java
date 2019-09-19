package com.capinfo.utils;

import com.capinfo.entity.CapCheckBook;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

public class ReflectUtils {
    public static Object getObjectValue(String fieldName,Object object,Field field) throws Exception {
        //我们项目的所有实体类都继承BaseDomain （所有实体基类：该类只是串行化一下）
        //不需要的自己去掉即可

            // 拿到该类
           // Class<?> clz = object.getClass();
            // 获取实体类的所有属性，返回Field数组
         //   Field[] fields = clz.getDeclaredFields();

           // for (Field field : fields) {// --for() begin
                //System.out.println(field.getGenericType());//打印该类的所有属性类型

                // 如果类型是String
                if (field.getGenericType().toString().equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                    // 拿到该属性的gettet方法
                    /**
                     * 这里需要说明一下：他是根据拼凑的字符来找你写的getter方法的
                     * 在Boolean值的时候是isXXX（默认使用ide生成getter的都是isXXX）
                     * 如果出现NoSuchMethod异常 就说明它找不到那个gettet方法 需要做个规范
                     */
                    Method m1 = (Method) object.getClass().getMethod("get" + getMethodName(fieldName));

                    String val1 = (String) m1.invoke(object);// 调用getter方法获取属性值
                  //
                    return val1;

                }

                // 如果类型是Integer
                if (field.getGenericType().toString().equals(
                        "class java.lang.Integer")) {
                    Method m2 = (Method) object.getClass().getMethod(
                            "get" + getMethodName(fieldName));
                    Integer val2 = (Integer) m2.invoke(object);


                    return val2;

                }

                // 如果类型是Double
                if (field.getGenericType().toString().equals(
                        "class java.lang.Double")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(fieldName));
                    Double val = (Double) m.invoke(object);

                    return val;

                }

                // 如果类型是Boolean 是封装类
                if (field.getGenericType().toString().equals(
                        "class java.lang.Boolean")) {
                    Method m = (Method) object.getClass().getMethod(
                            fieldName);
                    Boolean val = (Boolean) m.invoke(object);
                    return val;

                }

                // 如果类型是boolean 基本数据类型不一样 这里有点说名如果定义名是 isXXX的 那就全都是isXXX的
                // 反射找不到getter的具体名

                if (field.getGenericType().toString().equals("boolean")) {
                    Method m = (Method) object.getClass().getMethod(
                            fieldName);
                    Boolean val = (Boolean) m.invoke(object);
                   return val;

                }
                // 如果类型是Date
                if (field.getGenericType().toString().equals(
                        "class java.util.Date")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(fieldName));
                    Date val = (Date) m.invoke(object);
                   return val;

                }
                // 如果类型是Short
                if (field.getGenericType().toString().equals(
                        "class java.lang.Short")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(fieldName));
                    Short val = (Short) m.invoke(object);
                    return val;

                }
                // 如果还需要其他的类型请自己做扩展

          //  }//for() --end
        return null;

    }

    // 把一个字符串的第一个字母大写、效率是最高的、
    public static String getMethodName(String fieldName) throws Exception{
        byte[] items = fieldName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

   public static void main(String[] args) throws Exception {
       CapCheckBook checkBook = new CapCheckBook();
        checkBook.setCheckPerson("1");
        Field[] fields = checkBook.getClass().getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            String fieldName = field.getName();
            System.out.println(getObjectValue(fieldName,checkBook,field));
        }
       //getObjectValue(checkBook);
    }
}
