package com.mfa.repositories;

import com.mfa.datas.entities.MfaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MfaRepository extends JpaRepository<MfaEntity, Long> {

    MfaEntity findByUsername(String username);

}
