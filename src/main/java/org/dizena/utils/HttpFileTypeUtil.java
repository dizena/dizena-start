package org.dizena.utils;

import java.util.HashMap;
import java.util.Map;

public class HttpFileTypeUtil
{

    public static String getContentType(String fileName)
    {
        String res = "application/octet-stream";
        try
        {
            String key = fileName.substring(fileName.lastIndexOf("."));
            res = kvMap.get(key);
            if (res == null)
            {
                res = "application/octet-stream";
            }
        } catch (Exception ignored)
        {
        }
        return res;
    }

    public static String getContentTypeByExt(String fileExtName)
    {
        String res = kvMap.get(fileExtName);
        if (res == null)
        {
            res = "application/octet-stream";
        }
        return res;
    }

    private static final Map<String, String> kvMap = new HashMap<>();

    static
    {
        kvMap.put(".json", "application/json");
        kvMap.put(".asp", "text/asp");
        kvMap.put(".mp3", "audio/mp3");
        kvMap.put(".pdf", "application/pdf");
        kvMap.put(".png", "image/png");
        kvMap.put(".ppt", "application/vnd.ms-powerpoint");
        kvMap.put(".svg", "text/xml");
        kvMap.put(".tld", "text/xml");
        kvMap.put(".torrent", "application/x-bittorrent");
        kvMap.put(".doc", "application/msword");
        kvMap.put(".txt", "text/plain");
        kvMap.put(".vml", "text/xml");
        kvMap.put(".gif", "image/gif");
        kvMap.put(".jpeg", "image/jpeg");
        kvMap.put(".jpg", "image/jpeg");
        kvMap.put(".ico", "image/x-icon");
        kvMap.put(".img", "application/x-img");
        kvMap.put(".java", "java/*");
        kvMap.put(".jpe", "image/jpeg");
        kvMap.put(".jsp", "text/");
        kvMap.put(".mp4", "video/mpeg4");
        kvMap.put(".swf", "application/x-shockwave-flash");
        kvMap.put(".vcf", "text/x-vcard");
        kvMap.put(".wav", "audio/wav");
        kvMap.put(".wmv", "video/x-ms-wmv");
        kvMap.put(".xls", "application/x-xls");
        kvMap.put(".xml", "text/xml");
        kvMap.put(".xsl", "text/xml");
        kvMap.put(".apk", "application/vnd.android.package-archive");
        kvMap.put(".tif", "image/tiff");
        kvMap.put(".avi", "video/avi");
        kvMap.put(".css", "text/css");
        kvMap.put(".dtd", "text/xml");
        kvMap.put(".exe", "application/x-msdownload");
        kvMap.put(".htm", "text/html");
        kvMap.put(".html", "text/html");
        kvMap.put(".js", "application/x-javascript");
    }

}
