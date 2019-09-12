package com.caine.platform.gateway.filter;

import com.caine.platform.common.constant.Constant;
import com.caine.platform.gateway.interfaces.IAutoRefreshToken;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

/**
 * @Author: CaineZhu
 * @Description:
 * @Date: Created in 14:42 2019/9/11
 * @Modified By:
 */
@Component
public class ResponseFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(ResponseFilter.class);

    @Value("${com.caine.token.autorefresh:false}")
    private boolean autoRefresh;

    @Autowired
    private IAutoRefreshToken autoRefreshToken;

    @Override
    public String filterType() {
        return POST_TYPE;
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
        try {
            RequestContext requestContext = RequestContext.getCurrentContext();
            HttpServletResponse response = autoRefreshToken.autoRefreshToken(requestContext.getRequest(), requestContext.getResponse());
//            String body = StreamUtils.copyToString(requestContext.getResponseDataStream(), Charset.forName(Constant.DEFAULT_CHARSET));
            requestContext.setResponse(response);
        } catch (Exception e) {
            logger.error("异常", e);
        }
        return null;
    }
}
