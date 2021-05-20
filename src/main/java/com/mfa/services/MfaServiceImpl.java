package com.mfa.services;

import com.mfa.datas.dto.MfaDto;
import com.mfa.datas.dto.MfaInitDto;
import com.mfa.datas.dto.MfaProviderDto;
import com.mfa.datas.entities.MfaEntity;
import com.mfa.repositories.MfaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MfaServiceImpl implements MfaService {

    private final MfaRepository mfaRepository;

    @Autowired

    public MfaServiceImpl(MfaRepository mfaRepository) {
        this.mfaRepository = mfaRepository;
    }

    @Override
    public MfaDto getMfa(String username) {
        return new MfaDto(mfaRepository.findByUsername(username));
    }

    @Override
    public MfaProviderDto getMfaSecretKey(String username) {
        return new MfaProviderDto(mfaRepository.findByUsername(username));
    }

    @Override
    public MfaInitDto setMfa(MfaInitDto mfaInitDto) {
        mfaRepository.save(new MfaEntity(mfaInitDto));
        return mfaInitDto;
    }

    @Override
    public MfaDto setMfa(MfaDto mfaDto) {
        mfaRepository.save(new MfaEntity(mfaDto));
        return mfaDto;
    }

    @Override
    public void deleteMfa(String username) {
        mfaRepository.delete(mfaRepository.findByUsername(username));
    }

}
