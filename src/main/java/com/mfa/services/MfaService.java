package com.mfa.services;

import com.mfa.datas.dto.MfaDto;
import com.mfa.datas.dto.MfaInitDto;
import com.mfa.datas.dto.MfaProviderDto;

public interface MfaService {
    MfaDto getMfa(String username);

    MfaProviderDto getMfaSecretKey(String username);

    MfaInitDto setMfa(MfaInitDto mfaInitDto);

    MfaDto setMfa(MfaDto mfaDto);

    void deleteMfa(String username);
}
