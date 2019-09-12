package com.caine.platform.gateway.interfaces;

import com.caine.platform.common.exception.CustomException;

import java.util.Map;

/**
 * @Author: CaineZhu
 * @Description:
 * @Date: Created in 15:46 2019/9/4
 * @Modified By:
 */
public interface IKeywordsValidator<T> {

    /**
     * 参数过滤
     * @param parameters
     */
    void filter(T parameters) throws CustomException;
}
