package org.dizena.utils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class NetDownUtil
{

    public static InputStream down(String url)
    {
        InputStream in = null;
        try
        {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();

            in = connection.getInputStream();


        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return in;
    }
}
