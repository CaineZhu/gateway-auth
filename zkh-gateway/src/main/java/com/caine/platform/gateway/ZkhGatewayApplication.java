package com.caine.platform.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Author: CaineZhu
 * @Description: gateway启动类
 * @Date: Created in 17:43 2019/9/3
 * @Modified By:
 */
@SpringBootApplication
@EnableZuulProxy
public class ZkhGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZkhGatewayApplication.class, args);
    }

}
