package org.dizena.utils;

/**
 * 判断模板变量个数工具
 */
public class AIStringUtil {
    public static int countSubstring(String str, String substr) {
        int count = 0;
        int lastIndex = 0;
        while (lastIndex != -1) {
            lastIndex = str.indexOf(substr, lastIndex);
            if (lastIndex != -1) {
                count++;
                lastIndex += substr.length();
            }
        }
        return count;
    }

}
