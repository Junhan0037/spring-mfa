package com.mfa.exception;

import org.springframework.security.core.AuthenticationException;

public class OtpNotApproveException extends AuthenticationException {

    public OtpNotApproveException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public OtpNotApproveException(String msg) {
        super(msg);
    }

}
