package com.caine.platform.gateway.validation;

import com.caine.platform.common.constant.Constant;
import com.caine.platform.common.security.SHA256Tool;
import com.caine.platform.gateway.interfaces.IParamValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @Author: CaineZhu
 * @Description:
 * @Date: Created in 14:31 2019/9/17
 * @Modified By:
 */
public class ParamValidator implements IParamValidator {

    Logger logger = LoggerFactory.getLogger(ParamValidator.class);

    /**
     * 使用SHA-256哈希 SHA-256(param+timeStamp+random)后的值同signtrue做对比如果一直则通过,否则不通过
     * 需要通过3次校验参数 1:时间浮动在4s内  2:随机数在30分钟内不能重复 3:整体参数做sha-256签名
     * 1和2是防止重放攻击和多次提交 3是做数据防篡改
     * @param param     实际参数(加密后)
     * @param timeStamp 时间戳
     * @param random    随机数
     * @param signtrue  参数签名
     * @return
     */
    @Override
    public boolean validation(RedisTemplate redisTemplate, String param, long timeStamp, String random, String signtrue) {
        /**
         * 可以先校验时间控制请求和服务时间不超过xxx时间(阈值可以自己定)
         */
        long currentTimeStamp = System.currentTimeMillis() / 1000L;
        long limit = currentTimeStamp - timeStamp / 1000L;
        //客户端时间不能比服务器快,实际情况可能要支持几秒的时间浮动(实际同步时间和网络延迟都需要考虑)
        //总的来说就是上下浮动4S时间
        if (limit < Constant.REQ_LIMITS || limit > Math.abs(Constant.REQ_LIMITS)) {
            logger.info("客户端请求时间和服务器处理时间相差:{}", limit);
            return false;
        }

        //验证随机数
        boolean hasKey = redisTemplate.hasKey(Constant.REQ_RANDOM_PREFIX + random);
        if (hasKey) {
            logger.info("重复随机数key:{}", Constant.REQ_RANDOM_PREFIX + random);
            return false;
        }
        //存入随机数有效时间默认30分钟
        redisTemplate.opsForValue().set(Constant.REQ_RANDOM_PREFIX + random, random, 30L, TimeUnit.MINUTES);
        String fullStr = param + timeStamp + random;
        String sign = SHA256Tool.getSHA256(fullStr);
        return sign.equals(signtrue);
    }
}
