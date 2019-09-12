package com.caine.platform.gateway.filter;

import com.caine.platform.common.ResultMap;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: CaineZhu
 * @Description:
 * @Date: Created in 19:16 2019/9/9
 * @Modified By:
 */
@RestController
public class ErrorHandlerController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    @ResponseBody
    public ResultMap error(HttpServletRequest request){
        Integer status = (Integer)request.getAttribute("javax.servlet.error.status_code");
        return new ResultMap().failed(status == 404 ? "访问地址不存在" : "内部服务器错误,正在处理");
    }
}
