package com.makenv.model.mc.message.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.makenv.model.mc.core.util.JacksonUtil;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wgy on 2017/2/21.
 */
public class BeanUtil {


    // Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
    public static Object transMap2Bean(Map<String, Object> map, Object obj) {

        try {

            String json = JacksonUtil.objToJson(map);

            obj = JacksonUtil.jsonToObj(json,obj.getClass());

        } catch (Exception e) {

            e.printStackTrace();
        }

        return obj;

    }

    public static Object transforMapToObject(Class<? extends Object> type, Map<String, String> map) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); //获取类属性
        Object obj = type.newInstance(); //创建 JavaBean 对象
//给 JavaBean对象的属性赋值
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (map.containsKey(propertyName)) {
                try {
                    Object value = map.get(propertyName);
                    Object[] args = new Object[1];
                    args[0] = value;
                    descriptor.getWriteMethod().invoke(obj, args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }

    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
    public static Map<String, Object> transBean2Map(Object obj) {

        if(obj == null){
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }

        return map;

    }
}
