package com.mfa;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncodingTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("1234"));
    }
}
