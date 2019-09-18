package com.caine.platform.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.caine.platform.common.ResultMap;
import com.caine.platform.common.StringTools;
import com.caine.platform.common.constant.Constant;
import com.caine.platform.common.security.DESTools;
import com.caine.platform.gateway.interfaces.IParamValidator;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @Author: CaineZhu
 * @Description:
 * @Date: Created in 17:43 2019/9/3
 * @Modified By:
 */
@Component
public class ParamFilter extends ZuulFilter {
    private Logger logger = LoggerFactory.getLogger(ParamFilter.class);

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired(required = false)
    IParamValidator paramValidator;

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
        RequestContext ctx = RequestContext.getCurrentContext();
        if (!ctx.sendZuulResponse()) {
            return false;
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        try {
            String body = StreamUtils.copyToString(request.getInputStream(), Charset.forName(Constant.DEFAULT_CHARSET));
            JSONObject object = JSONObject.parseObject(body);
            String param = object.getString("param");
            String timeStamp = object.getString("timestamp");
            String random = object.getString("random");
            String signtrue = object.getString("signtrue");
            if (StringTools.isNullOrEmpty(param, timeStamp, random, signtrue)) {
                responseErr(ctx, "参数不合法");
                return null;
            }
            //参数合法性校验
            if (null != paramValidator) {
                boolean validator = paramValidator.validation(redisTemplate, param, Long.parseLong(timeStamp), random, signtrue);
                if (!validator) {
                    responseErr(ctx, "参数不合法");
                    return null;
                }
            }
            //实际参数解密
            String parameters = DESTools.decrypt(param);
            byte[] reqBodyBytes = parameters.getBytes(Constant.DEFAULT_CHARSET);
            ctx.setRequest(new HttpServletRequestWrapper(request) {
                @Override
                public ServletInputStream getInputStream() throws IOException {
                    return new ServletInputStreamWrapper(reqBodyBytes);
                }

                @Override
                public int getContentLength() {
                    return reqBodyBytes.length;
                }

                @Override
                public long getContentLengthLong() {
                    return reqBodyBytes.length;
                }
            });
        } catch (Exception e) {
            logger.error("has exception", e);
        }
        return null;
    }

    /**
     * 参数相关错误输出
     *
     * @param ctx
     * @param msg
     * @throws IOException
     */
    private void responseErr(RequestContext ctx, String msg) throws IOException {
        ctx.setSendZuulResponse(false);
        HttpServletResponse response = ctx.getResponse();
        response.setCharacterEncoding(Constant.DEFAULT_CHARSET);
        response.setHeader("content-type", "application/json;charset=utf8");
        String ret = JSONObject.toJSONString(new ResultMap().failed(msg));
        response.getWriter().print(ret);
        ctx.setResponse(response);
    }
}
