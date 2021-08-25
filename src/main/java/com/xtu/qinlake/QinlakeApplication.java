package com.xtu.qinlake;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
@MapperScan("com.xtu.qinlake.mapper")
public class QinlakeApplication {

    public static void main(String[] args) {
        SpringApplication.run(QinlakeApplication.class, args);
    }

}
