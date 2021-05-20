package com.mfa.datas.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class MfaInitDto implements Serializable {

    private String username;
    private String secretKey;
    private String type;

    @Builder
    public MfaInitDto(String username, String secretKey, String type) {
        this.username = username;
        this.secretKey = secretKey;
        this.type = type;
    }

}
