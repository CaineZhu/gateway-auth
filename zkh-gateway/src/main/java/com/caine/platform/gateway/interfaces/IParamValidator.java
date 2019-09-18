package com.caine.platform.gateway.interfaces;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Author: CaineZhu
 * @Description: 权限验证器
 * @Date: Created in 10:46 2019/9/6
 * @Modified By:
 */
public interface IParamValidator {

    /**
     *
     * @param param  实际参数(加密后)
     * @param timeStamp 时间戳
     * @param random 随机数
     * @param signtrue 参数签名
     * @return
     */
    boolean validation(RedisTemplate redisTemplate, String param, long timeStamp, String random, String signtrue);
}
