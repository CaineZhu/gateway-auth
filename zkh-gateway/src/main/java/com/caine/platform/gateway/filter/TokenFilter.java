package com.caine.platform.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.caine.platform.common.ResultMap;
import com.caine.platform.common.UrlMatcherTools;
import com.caine.platform.common.constant.Constant;
import com.caine.platform.common.exception.CustomException;
import com.caine.platform.common.pojo.UserInfo;
import com.caine.platform.gateway.interfaces.IAuthorityValidator;
import com.caine.platform.gateway.interfaces.IKeywordsValidator;
import com.caine.platform.gateway.interfaces.ITokenValidator;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @Author: CaineZhu
 * @Description:
 * @Date: Created in 17:43 2019/9/3
 * @Modified By:
 */
@Component
public class TokenFilter extends ZuulFilter {
    private Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    /**
     * token验证器
     */
    @Autowired(required = false)
    private ITokenValidator<UserInfo> tokenValidator;

    /**
     * 关键字验证器
     */
    @Autowired(required = false)
    private IKeywordsValidator keywordsValidator;

    /**
     * 权限验证器
     */
    @Autowired(required = false)
    private IAuthorityValidator authorityValidator;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        String matcher = UrlMatcherTools.urlMatcher(RequestContext.getCurrentContext().getRequest().getRequestURI().toString(), "");
        if (matcher == null) {
            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String reqUrl = request.getRequestURL().toString();
        try {
            String token = request.getHeader("token");
            if (null == token) {
                ctx.setSendZuulResponse(false);
                HttpServletResponse response = ctx.getResponse();
                response.setCharacterEncoding(Constant.DEFAULT_CHARSET);
                response.getWriter().write(JSONObject.toJSONString(new ResultMap().error("请登录")));
                ctx.setResponse(response);
            } else {
                UserInfo tokenInfo = tokenValidator.validation(token);
                //token通过逻辑
                if (null != tokenInfo) {
                    try {
                        //权限验证器
                        if (null != authorityValidator) {
                            boolean authority = authorityValidator.check(reqUrl, token);
                            if (!authority) {
                                ctx.setSendZuulResponse(false);
                                HttpServletResponse response = ctx.getResponse();
                                response.setCharacterEncoding(Constant.DEFAULT_CHARSET);
                                response.getWriter().write(JSONObject.toJSONString(new ResultMap().failed("权限不足")));
                            }
                        }
                        //关键字验证器
                        request = new CustomRequestWrapper(request, keywordsValidator);
                        ctx.setRequest(request);
                    } catch (CustomException e) {
                        ctx.setSendZuulResponse(false);
                        HttpServletResponse response = ctx.getResponse();
                        response.setCharacterEncoding(Constant.DEFAULT_CHARSET);
                        response.getWriter().write(JSONObject.toJSONString(e.getLevel() == Constant.EXCEPTION_LEVEL_1 ? new ResultMap().error(e.getMessage()) : new ResultMap().failed(e.getMessage())));
                    }
                } else {
                    ctx.setSendZuulResponse(false);
                    HttpServletResponse response = ctx.getResponse();
                    response.setCharacterEncoding(Constant.DEFAULT_CHARSET);
                    response.getWriter().write(JSONObject.toJSONString(new ResultMap().error("请登录")));
                }
            }
        } catch (IOException e) {
            logger.error("token filter IOException", e);
        }
        return null;
    }
}
