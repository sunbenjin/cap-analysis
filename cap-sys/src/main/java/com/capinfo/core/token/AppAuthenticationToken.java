package com.capinfo.core.token;

import org.apache.shiro.authc.AuthenticationToken;

public class AppAuthenticationToken implements AuthenticationToken {
    private static final long serialVersionUID = 519862653019427411L;

    /**
     * The username
     */
    private String username;

    /**
     * The password, in char[] format
     */
    private char[] password;

    private String token;

    public AppAuthenticationToken() {
    }

    public AppAuthenticationToken(String token) {
        this.token = token;
    }

    public AppAuthenticationToken(final String username, final String password) {
        this(username, password != null ? password.toCharArray() : null);
    }

    public AppAuthenticationToken(final String username, final char[] password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return getUsername();
    }

    @Override
    public Object getCredentials() {
        return getPassword();
    }

    public void clear() {
        this.username = null;

        if (this.password != null) {
            for (int i = 0; i < password.length; i++) {
                this.password[i] = 0x00;
            }
            this.password = null;
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append(" - ");
        sb.append(username);
        if (token != null) {
            sb.append(" (").append(token).append(")");
        }
        return sb.toString();
    }
}
