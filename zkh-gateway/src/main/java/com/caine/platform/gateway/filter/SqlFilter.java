package com.caine.platform.gateway.filter;

import com.caine.platform.common.exception.CustomException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * sql过滤
 *
 * @Author: CaineZhu
 * @Description:
 * @Date: Created in 11:14 2019/8/6
 * @Modified By:
 */
public class SqlFilter {

    private static final Logger logger = LoggerFactory.getLogger(SqlFilter.class);

    /**
     * SQL注入过滤
     *
     * @param str 待验证的字符串
     */
    public static String sqlInject(String str) throws CustomException {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        // 去掉'|"|;|\字符
        str = StringUtils.replace(str, "'", "");
        str = StringUtils.replace(str, "\"", "");
        str = StringUtils.replace(str, ";", "");
        str = StringUtils.replace(str, "\\", "");

        // 转换成小写
        str = str.toLowerCase();

        // 非法字符
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alert",
                "create", "drop"};
        // 判断是否包含非法字符
        for (String keyword : keywords) {
            if (str.contains(keyword)) {
                throw new CustomException().error(new Exception("包含非法字符"));
            }
        }

        return str;
    }

    /**
     * SQL注入过滤
     *
     * @param arr 待验证的字符串
     */
    public static String hasKeyword(String... arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }

        // 非法字符
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alert",
                "create", "drop"};
        for (String item : arr) {
            // 判断是否包含非法字符
            for (String keyword : keywords) {
                if (item.trim().toLowerCase().contains(keyword)) {
                    logger.info("包含非法字符{}", keyword);
                    return keyword;
                }
            }
        }

        return null;
    }
}