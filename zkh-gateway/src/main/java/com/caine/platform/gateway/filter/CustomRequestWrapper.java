package com.caine.platform.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.caine.platform.common.StringTools;
import com.caine.platform.common.constant.Constant;
import com.caine.platform.common.exception.CustomException;
import com.caine.platform.gateway.interfaces.IKeywordsValidator;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * 封装request参数
 *
 * @Author: CaineZhu
 * @Description:
 * @Date: Created in 11:14 2019/8/6
 * @Modified By:
 */
public class CustomRequestWrapper extends HttpServletRequestWrapper {
    Logger logger = LoggerFactory.getLogger(CustomRequestWrapper.class);

    private final byte[] body;

    /**
     * @param request
     * @param keywordsFilter 传入filter实现类,可以为null, null默认不过滤参数
     * @throws CustomException
     */
    public CustomRequestWrapper(HttpServletRequest request, IKeywordsValidator keywordsFilter) throws CustomException {
        super(request);
        String sessionStream = getBodyString(request);
        Map<String, Object> parameters = (Map<String, Object>) JSONObject.parse(sessionStream);
        if (null != keywordsFilter) {
            keywordsFilter.filter(parameters);
        }
        body = sessionStream.getBytes(Charset.forName(Constant.DEFAULT_CHARSET));
    }


//    ============表单方式提交==============

    @Override
    public String getParameter(String name) {
        return getValue(name);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameterValues = super.getParameterValues(name);
        if (parameterValues == null) {
            return null;
        }
        for (int i = 0; i < parameterValues.length; i++) {
            String value = parameterValues[i];
            parameterValues[i] = StringEscapeUtils.escapeHtml4(value);
        }
        return parameterValues;
    }

    /**
     * 获取参数再次包装
     *
     * @param name
     * @return
     */
    private String getValue(String name) {
        String url = getRequestURL().toString();
        String authorization = getHeader("Authorization") == null ? null : getHeader("Authorization");
        if (!url.contains("/accessToken") && authorization != null) {
            if (authorization.toLowerCase().startsWith("bearer")) {
                authorization = authorization.substring(7, authorization.length());
            }
            //从token里获取请求用户的信息--后期包装到@RequestBody里
//            switch (name) {
//                case "user_id":
//                    return TokenUtil.getUserId(authorization);
//                case "role_id":
//                    return TokenUtil.getUserRoleId(authorization);
//                case "company_id":
//                    return TokenUtil.getUserCompanyId(authorization);
//                default:
//            }

        }

        String value = super.getParameter(name);
        if (!StringTools.isNullOrEmpty(value)) {
            value = StringEscapeUtils.escapeHtml4(value);
        }
        return value;
    }

//    ===========================JSON方式提交======================

    /**
     * 获取请求Body
     *
     * @param request
     * @return
     */
    public String getBodyString(final ServletRequest request) throws CustomException {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = cloneInputStream(request.getInputStream());
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw new CustomException().normal(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new CustomException().normal(e);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new CustomException().normal(e);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 复制输入流
     *
     * @param inputStream
     * @return</br>
     */
    public InputStream cloneInputStream(ServletInputStream inputStream) throws CustomException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buffer)) > -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
        } catch (IOException e) {
            throw new CustomException().normal(e);
        }
        InputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return byteArrayInputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() {

        final ByteArrayInputStream bais = new ByteArrayInputStream(body);

        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }

    /**
     * 关键字过滤
     *
     * @param parameters
     * @throws CustomException
     */
    private void keywordsFilter(Map<String, Object> parameters) throws CustomException {
        if (parameters != null && parameters.size() > 0) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                Object obj = entry.getValue();
                if (obj instanceof String[]) {
                    String keyword = SqlFilter.hasKeyword((String[]) obj);
                    if (null != keyword) {
                        throw new CustomException().error(new Exception("参数包含关键字" + keyword));
                    }
                } else if (obj instanceof String) {
                    String keyword = SqlFilter.hasKeyword((String) obj);
                    if (null != keyword) {
                        throw new CustomException().error(new Exception("参数包含关键字" + keyword));
                    }
                }

            }
        }
    }
}
