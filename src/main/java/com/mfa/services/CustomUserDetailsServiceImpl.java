package com.mfa.services;

import com.mfa.datas.dto.CustomUserDetails;
import com.mfa.datas.entities.UserEntity;
import com.mfa.exception.OtpNotApproveException;
import com.mfa.util.OTPUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private final UserService userService;
    private final MfaService mfaService;
    private String otp;

    public CustomUserDetailsServiceImpl(UserService userService, MfaService mfaService) {
        this.userService = userService;
        this.mfaService = mfaService;
    }

    @Override
    public UserDetails loadUserByUsername(String username, String otp) throws UsernameNotFoundException, OtpNotApproveException {
        this.otp = otp;

        if (otp != null) {
            String secretKey = mfaService.getMfaSecretKey(username).getSecretKey();
            if (OTPUtil.checkCode(otp, secretKey)) {
                throw new OtpNotApproveException("OTP number didn't approve. Please check again.");
            }
        }

        return loadUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userService.getUser(username);

        if (userEntity == null) {
            throw new UsernameNotFoundException("The user not exist. Please check again.");
        }

        CustomUserDetails.CustomUserDetailsBuilder customUserDetailsBuilder = CustomUserDetails.builder();
        customUserDetailsBuilder
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .accountNonExpired(false)
                .accountNonLocked(true)
                .credentialsNonExpired(false)
                .enabled(true);

        return customUserDetailsBuilder.build();
    }

}
