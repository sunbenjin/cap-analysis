package com.capinfo.exception;

import org.apache.shiro.authc.AuthenticationException;

public class LoginRequestMethodNotSupportException extends AuthenticationException {

    private static final long serialVersionUID = -8348478746031256430L;

    public LoginRequestMethodNotSupportException() {
        super();
    }

    public LoginRequestMethodNotSupportException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginRequestMethodNotSupportException(String message) {
        super(message);
    }

    public LoginRequestMethodNotSupportException(Throwable cause) {
        super(cause);
    }


}
