package com.caine.platform.common;

/**
 * @Author: CaineZhu
 * @Description:
 * @Date: Created in 9:51 2019/9/5
 * @Modified By:
 */
public class UrlMatcherTools {

    public static String urlMatcher(String sourceUrl, String... urlsPool){
        if(sourceUrl.startsWith("/api/token/accessToken")){
            return sourceUrl;
        }
        return null;
    }
}
