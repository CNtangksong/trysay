package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.demo.mapper")//来使用mabatis生成的mapper
public class TrysayApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrysayApplication.class, args);
    }

}
