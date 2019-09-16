package com.caine.platform.common;

/**
 * @Author: CaineZhu
 * @Description:
 * @Date: Created in 9:51 2019/9/5
 * @Modified By:
 */
public class UrlMatcherTools {

    private static final String MATCH_SIGN = "*";
    private static final String MATCH_SIGNS = "**";

    public static String urlMatcher(String sourceUrl, String[] defaultWhiteLst, String[] urlsPool) {
        if (!StringTools.isNullOrEmpty(sourceUrl)) {
            if (null != defaultWhiteLst && defaultWhiteLst.length > 0) {
                for(String v : defaultWhiteLst) {
                    if (v.endsWith(MATCH_SIGNS)) {
                        String vTmp = v.substring(0, v.lastIndexOf(MATCH_SIGNS));
                        if(sourceUrl.startsWith(vTmp)){
                            return v;
                        }
                    }

                    if (v.endsWith(MATCH_SIGN)) {
                        String vTmp = v.substring(0, v.lastIndexOf(MATCH_SIGN));
                        if(sourceUrl.startsWith(vTmp)){
                            return v;
                        }
                    }
                }
            }
            if (null != urlsPool && urlsPool.length > 0) {
                for(String v : urlsPool) {
                    if (v.endsWith(MATCH_SIGNS)) {
                        String vTmp = v.substring(0, v.lastIndexOf(MATCH_SIGNS));
                        if(sourceUrl.startsWith(vTmp)){
                            return v;
                        }
                    }
                    if (v.endsWith(MATCH_SIGN)) {
                        String vTmp = v.substring(0, v.lastIndexOf(MATCH_SIGN));
                        if(sourceUrl.startsWith(vTmp)){
                            return v;
                        }
                    }
                }
            }
        }
        return null;
    }

    public static String urlMatcher(String sourceUrl, String... urlsPool) {
        return urlMatcher(sourceUrl, null, urlsPool);
    }

    public static void main(String[] args) {
        String urls = "/api/user/info,/api/company/*,/api/user/**";
        String ret = urlMatcher("/api/user/add",urls.split(","));
        System.out.println(ret);
    }
}
