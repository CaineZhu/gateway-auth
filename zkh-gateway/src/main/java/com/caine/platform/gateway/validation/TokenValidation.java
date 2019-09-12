package com.caine.platform.gateway.validation;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.caine.platform.common.constant.Constant;
import com.caine.platform.common.pojo.UserInfo;
import com.caine.platform.gateway.interfaces.ITokenValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;

import java.util.List;

/**
 * @Author: CaineZhu
 * @Description:
 * @Date: Created in 16:15 2019/9/6
 * @Modified By:
 */
public class TokenValidation implements ITokenValidator<UserInfo> {

    Logger logger = LoggerFactory.getLogger(TokenValidation.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public UserInfo validation(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            List<String> audienceLst = decodedJWT.getAudience();
            String redisKey = Constant.TOKEN_PREFIX.replace("{0}", audienceLst.get(1)) + DigestUtils.md5DigestAsHex(token.getBytes(Constant.DEFAULT_CHARSET));
            logger.info(">>>>>>>>>token redisKey:{}", redisKey);
            UserInfo obj = (UserInfo) redisTemplate.opsForValue().get(redisKey);
            if (obj != null) {
                return obj;
            }
        } catch (Exception e) {
            logger.error("validation has exception", e);
        }
        return null;
    }
}
