package com.capinfo.exception;

import org.apache.shiro.authc.AuthenticationException;

public class InvalidAccessTokenAuthenticationException extends AuthenticationException {

    private static final long serialVersionUID = 410131848322225824L;

    public InvalidAccessTokenAuthenticationException() {
        super();
    }

    public InvalidAccessTokenAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAccessTokenAuthenticationException(String message) {
        super(message);
    }

    public InvalidAccessTokenAuthenticationException(Throwable cause) {
        super(cause);
    }
}
