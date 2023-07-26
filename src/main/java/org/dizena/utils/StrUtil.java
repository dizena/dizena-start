package org.dizena.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StrUtil
{
    public static String makeNumCode()
    {
        StringBuilder s = new StringBuilder();
        String st = "0123456789";
        char[] str = st.toCharArray();
        for (int i = str.length - 1; i >= str.length - 6; i--)
        {
            int k = (int) (Math.random() * i);
            char t = str[k];// 将随机生成的拿出来
            str[k] = str[i];// 将生成不到的放入可能生成的地方
            str[i] = t;
            s.append(t);
        }
        return s.toString();
    }

    public static List<String> str2List(String str, String reg)
    {
        String[] ss = str.split(reg);
        return new ArrayList<>(Arrays.asList(ss));
    }

}
