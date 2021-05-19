package com.mfa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.mfa"})
public class MfaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MfaApplication.class, args);
    }

}
