package com.mfa.util;

import org.apache.commons.codec.binary.Base32;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class OTPUtil {


    public static HashMap<String, Object> generate(String productName, String username) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        byte[] buffer = new byte[5 + 5 * 5]; //버퍼에 할당
        new SecureRandom().nextBytes(buffer); //버퍼를 난수로 셋팅
        Base32 codec = new Base32();
        byte[] secretKey = Arrays.copyOf(buffer, 10);
        byte[] bEncodedKey = codec.encode(secretKey);
        String encodedKey = new String(bEncodedKey); //생성된 Key (해당키로 OTP확인)
        String url = getQRBarcodeURL(productName, username, encodedKey);
        map.put("code", encodedKey);
        map.put("codeUrl", url);
        return map;
    }

    //OTP코드 6자리 Check
    public static boolean checkCode(String otpNumber, String secretKey) {
        long otpnum = Integer.parseInt(otpNumber); // OTP 앱에 표시되는 6자리 숫자
        long wave = new Date().getTime() / 30000; // OTP의 주기(초)
        boolean result = false;
        try {
            Base32 codec = new Base32();
            byte[] decodedKey = codec.decode(secretKey);
            int window = 3;
            for (int i = -window; i <= window; ++i) {
                long hash = verifyCode(decodedKey, wave + i);
                if (hash == otpnum) result = true;
            }
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static int verifyCode(byte[] key, long t) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] data = new byte[8];
        long value = t;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }
        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(data);
        int offset = hash[20 - 1] & 0xF;
        // We're using a long because Java hasn't got unsigned int.
        long truncatedHash = 0;
        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            // We are dealing with signed bytes:
            // we just keep the first byte.
            truncatedHash |= (hash[offset + i] & 0xFF);
        }
        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= 1000000;
        return (int) truncatedHash;
    }

    public static String getQRBarcodeURL(String productName, String userName, String secret) {
        // QR코드 주소 생성
        String codeURL = "http://chart.apis.google.com/chart?cht=qr&chs=200x200&chl=otpauth://totp/%s(%s)%%3Fsecret%%3D%s&chld=H|0";
        return String.format(codeURL, productName, userName, secret);
    }
}
