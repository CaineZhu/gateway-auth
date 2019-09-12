package com.caine.platform.gateway.interfaces;

/**
 * @Author: CaineZhu
 * @Description: 权限验证器
 * @Date: Created in 10:46 2019/9/6
 * @Modified By:
 */
public interface IAuthorityValidator {

    boolean check(String reqUrl, String token);
}
