package com.caine.platform.gateway.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: CaineZhu
 * @Description:
 * @Date: Created in 14:24 2019/9/11
 * @Modified By:
 */
public interface IAutoRefreshToken {
    HttpServletResponse autoRefreshToken(HttpServletRequest request, HttpServletResponse response);
}
