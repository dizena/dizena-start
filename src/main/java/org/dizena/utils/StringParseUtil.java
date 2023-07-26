package org.dizena.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringParseUtil {

    public static Long getLongFromString(String input) {
        // 去除非数字字符
        String digitsOnly = input.replaceAll("[^\\d.]", "");
        // 提取数字
        if (digitsOnly.matches("\\d+")) {
            return Long.parseLong(digitsOnly);
        }
        return null;
    }

    public static Integer getNumberFromString(String input) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String extractedNumber = matcher.group();
            return Integer.parseInt(extractedNumber);
        } else {
            return null;
        }
    }

    /**
     * @param url .
     * @return map .
     * @throws Exception exec.
     * @info URL带有QueryString信息进行提取
     */
    public static Map<String, String> queryStr2Map(String url) {
        Map<String, String> map = new HashMap<>();
        String queryStr = url;
        int i11 = url.indexOf("?");
        if (i11 > -1) {
            queryStr = url.substring(i11 + 1);
        }
        if (StringUtils.isNotEmpty(queryStr)) {
            if (queryStr.contains("&")) {
                String[] ss = queryStr.split("&");
                for (String s : ss) {
                    String[] kv = str2kv(s, "=");
                    if (kv != null) {
                        map.put(kv[0], kv[1]);
                    }
                }
            } else {
                String[] kv = str2kv(queryStr, "=");
                if (kv != null) {
                    map.put(kv[0], kv[1]);
                }
            }
        }
        return map;
    }

    private static String[] str2kv(String queryStr, String splitChar) {
        if (queryStr.contains(splitChar)) {
            String[] kvs = queryStr.split(splitChar);
            if (kvs != null && kvs.length == 2) {
                if (StringUtils.isNotEmpty(kvs[0]) && StringUtils.isNotEmpty(kvs[1])) {
                    return kvs;
                }
            }
        }
        return null;
    }

}
