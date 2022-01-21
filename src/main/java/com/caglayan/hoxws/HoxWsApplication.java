package com.caglayan.hoxws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class HoxWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HoxWsApplication.class, args);
    }

}
