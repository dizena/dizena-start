package org.dizena.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JSONUtil
{
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String toJSONString(Object obj)
    {
        try
        {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T jsonToObject(String jsonData, Class<T> beanType)
    {
        try
        {
            return MAPPER.readValue(jsonData, beanType);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType)
    {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try
        {
            return MAPPER.readValue(jsonData, javaType);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

}

