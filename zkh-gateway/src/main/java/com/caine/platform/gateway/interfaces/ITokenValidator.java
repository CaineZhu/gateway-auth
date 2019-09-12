package com.caine.platform.gateway.interfaces;

/**
 * @Author: CaineZhu
 * @Description:
 * @Date: Created in 10:46 2019/9/6
 * @Modified By:
 */
public interface ITokenValidator<T> {

    T validation(String token);
}
