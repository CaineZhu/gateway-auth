package com.caine.platform.gateway.component;

import com.caine.platform.gateway.interfaces.IAutoRefreshToken;
import com.caine.platform.gateway.interfaces.IParamValidator;
import com.caine.platform.gateway.interfaces.ITokenValidator;
import com.caine.platform.gateway.validation.AutoRefreshToken;
import com.caine.platform.gateway.validation.ParamValidator;
import com.caine.platform.gateway.validation.TokenValidation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author: CaineZhu
 * @Description:
 * @Date: Created in 16:18 2019/9/6
 * @Modified By:
 */
@Component
public class CommonCompent {

    @Bean
    @ConditionalOnMissingBean(TokenValidation.class)
    public ITokenValidator tokenValidator() {
//        return new TokenValidation();
        return null;
    }

    @Bean
    @ConditionalOnMissingBean(AutoRefreshToken.class)
    public IAutoRefreshToken autoRefreshToken() {
//        return new AutoRefreshToken();
        return null;
    }

    @Bean
    @ConditionalOnMissingBean(ParamValidator.class)
    public IParamValidator paramValidator() {
//        return new ParamValidator();
        return null;
    }


}
