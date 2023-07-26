package org.dizena.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ObjUtil {
    /**
     * 对象基本属性-转map，不可包含对象类型、数组；
     */
    public static Map<String, Object> beanAttrToMapKV(Object obj) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (obj != null) {
                Class<?> clazz = obj.getClass();
                for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true);
                    boolean isStatic = Modifier.isStatic(field.getModifiers());
                    if (!isStatic) {
                        String fieldName = field.getName();
                        Object value = field.get(obj);
                        if (value instanceof String) {
                            String sv = (String) value;
                            if (StringUtils.isNotEmpty(sv)) {
                                map.put(fieldName, value);
                            }
                        } else {
                            if (value != null) {
                                map.put(fieldName, value);
                            }
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }
}
