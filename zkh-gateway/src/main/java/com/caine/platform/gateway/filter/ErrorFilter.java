//package com.caine.platform.gateway.filter;
//
//import com.alibaba.fastjson.JSONObject;
//import com.caine.platform.common.ResultMap;
//import com.caine.platform.common.constant.Constant;
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.exception.ZuulException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletResponse;
//
//import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ERROR_TYPE;
//
///**
// * @Author: CaineZhu
// * @Description: error filter
// * @Date: Created in 17:43 2019/9/3
// * @Modified By:
// */
//@Component
//public class ErrorFilter extends ZuulFilter {
//    private Logger logger = LoggerFactory.getLogger(ErrorFilter.class);
//
//    @Override
//    public String filterType() {
//        return ERROR_TYPE;
//    }
//
//    @Override
//    public int filterOrder() {
//        return -1;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        return true;
//    }
//
//    @Override
//    public Object run() {
//        RequestContext context = RequestContext.getCurrentContext();
//        Throwable exception = context.getThrowable();
//        HttpServletResponse response = context.getResponse();
//        response.setContentType("application/json; charset=utf8");
//        context.setResponseBody(JSONObject.toJSONString(new ResultMap().error(exception.getCause().getMessage())));
//        context.setSendZuulResponse(false);
//        return null;
//    }
//}
