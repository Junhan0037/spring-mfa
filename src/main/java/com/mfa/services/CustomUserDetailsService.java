package com.mfa.services;

import com.mfa.exception.OtpNotApproveException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetailsService extends UserDetailsService {

    public UserDetails loadUserByUsername(String username, String otp) throws UsernameNotFoundException, OtpNotApproveException;

}
