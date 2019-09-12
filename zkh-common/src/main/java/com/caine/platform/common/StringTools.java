package com.caine.platform.common;

/**
 * @Author: CaineZhu
 * @Description:
 * @Date: Created in 10:50 2019/9/4
 * @Modified By:
 */
public class StringTools {

    /**
     * 对象空判断
     * @param obj
     * @return
     */
    public static boolean isNullOrEmpty(Object obj){
        if(obj == null || obj.toString().toString().equals("")){
            return true;
        }
        return false;
    }
}
