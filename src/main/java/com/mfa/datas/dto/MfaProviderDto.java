package com.mfa.datas.dto;

import com.mfa.datas.entities.MfaEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class MfaProviderDto implements Serializable {

    private String secretKey;
    private String type;

    public MfaProviderDto(MfaEntity mfaEntity) {
        this.secretKey = mfaEntity.getSecretKey();
        this.type = mfaEntity.getType();
    }

    @Builder
    public MfaProviderDto(String secretKey, String type) {
        this.secretKey = secretKey;
        this.type = type;
    }

}
