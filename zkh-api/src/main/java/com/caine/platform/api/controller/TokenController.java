package com.caine.platform.api.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.caine.platform.api.service.IUserInfoService;
import com.caine.platform.common.ResultMap;
import com.caine.platform.common.constant.Constant;
import com.caine.platform.common.pojo.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: CaineZhu
 * @Description:
 * @Date: Created in 11:14 2019/9/9
 * @Modified By:
 */

@RestController
@RequestMapping("token")
public class TokenController {

    private Logger logger = LoggerFactory.getLogger(TokenController.class);

    /**
     * token过期时间,默认1天
     */
    @Value("${com.caine.custom.token.expire:86400}")
    private long tokenExpire;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IUserInfoService userInfoService;

    /**
     * 获取token接口
     *
     * @param id
     * @return
     */
    @RequestMapping("accessToken")
    public ResultMap accessToken(long id) {
        Map<String, Object> userMap = new HashMap<>();
        try {
            UserInfo user = userInfoService.getById(id);
            String token = JWT.create().withAudience(user.getUserName(), String.valueOf(user.getId())).sign(Algorithm.HMAC256(user.getUserPass() + System.currentTimeMillis()));
            userMap.put("detail", user);
            userMap.put("token", token);
            String redisKey = Constant.TOKEN_PREFIX.replace("{0}",
                    String.valueOf(user.getId())) + DigestUtils.md5DigestAsHex(token.getBytes(Constant.DEFAULT_CHARSET));
            logger.info(">>>>>>>>>token redisKey:{}", redisKey);
            redisTemplate.opsForValue().set(redisKey,
                    user, tokenExpire, TimeUnit.SECONDS);
        } catch (Exception e) {
            return new ResultMap().failed("登录失败");
        }
        return new ResultMap().success(userMap);
    }
}
