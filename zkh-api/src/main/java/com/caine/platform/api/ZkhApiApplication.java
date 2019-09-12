package com.caine.platform.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.caine.platform.api.mapper"})
public class ZkhApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZkhApiApplication.class, args);
    }

}
