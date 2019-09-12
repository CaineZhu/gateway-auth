package com.caine.platform.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.caine.platform.common.ResultMap;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ERROR_TYPE;

/**
 * @Author: CaineZhu
 * @Description: error filter
 * @Date: Created in 17:43 2019/9/3
 * @Modified By:
 */
@Component
public class ErrorFilter extends ZuulFilter {
    private Logger logger = LoggerFactory.getLogger(ErrorFilter.class);

    @Override
    public String filterType() {
        return ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
//        RequestContext ctx = RequestContext.getCurrentContext();
//        HttpServletResponse response = ctx.getResponse();
//        response.setCharacterEncoding(Constant.DEFAULT_CHARSET);
//        try {
//            Throwable throwable = ctx.getThrowable();
//            response.getWriter().write(JSONObject.toJSONString(new ResultMap().error(throwable.getMessage())));
////            ctx.setResponse(response);
//        } catch (IOException e) {
//            logger.info("异常信息:{}", e);
//        }
        RequestContext context = RequestContext.getCurrentContext();
        ZuulException exception = this.findZuulException(context.getThrowable());
//        logger.error("进入系统异常拦截", exception);

        HttpServletResponse response = context.getResponse();
        response.setContentType("application/json; charset=utf8");
        response.setStatus(exception.nStatusCode);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.print(JSONObject.toJSONString(new ResultMap().error(exception.getCause().getMessage())));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

        return null;
    }

    ZuulException findZuulException(Throwable throwable) {
        if (ZuulRuntimeException.class.isInstance(throwable.getCause())) {
            return (ZuulException) throwable.getCause().getCause();
        } else if (ZuulException.class.isInstance(throwable.getCause())) {
            return (ZuulException) throwable.getCause();
        } else {
            return ZuulException.class.isInstance(throwable) ? (ZuulException) throwable : new ZuulException(throwable, 500, (String) null);
        }
    }
}
