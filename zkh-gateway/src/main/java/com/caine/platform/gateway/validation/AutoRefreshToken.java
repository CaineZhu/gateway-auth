package com.caine.platform.gateway.validation;

import com.caine.platform.common.StringTools;
import com.caine.platform.gateway.interfaces.IAutoRefreshToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: CaineZhu
 * @Description:
 * @Date: Created in 14:26 2019/9/11
 * @Modified By:
 */
public class AutoRefreshToken implements IAutoRefreshToken {
    private Logger logger = LoggerFactory.getLogger(AutoRefreshToken.class);

    @Override
    public HttpServletResponse autoRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refresh_token = request.getHeader("refresh-token");
        if (!StringTools.isNullOrEmpty(refresh_token)) {
            // 生成新的token
            String newToken = null;
            if (newToken != null) {
                response.setHeader("token", newToken);
                response.setHeader("Access-Control-Expose-Headers", "token");
                response.setHeader("Access-Control-Allow-Headers", "token");
                logger.info(">>>>>>>>>>>>>>>>>>>请求URL:[ip:{}]{} 后台刷新新令牌:{}", request.getRemoteAddr(), request.getRequestURI(), newToken);
            } else {
                logger.info(">>>>>>>>>>>>>>>>>>>>后台刷新token失败,下次再获取");
            }
        }
        return response;
    }
}
