package com.capinfo.core.utils;

import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenUtils {
    public static final String AUTHORIZATION = "Authorization";
    private static final Pattern OAUTH_HEADER = Pattern.compile("\\s*(\\w*)\\s+(.*)");
    public static final String OAUTH_HEADER_NAME = "Bearer";

    public static final String AUTH_SCHEME = OAUTH_HEADER_NAME;

    public static String getAuthHeaderAccessToken(ServletRequest request) {

        String authHeader = WebUtils.toHttp(request).getHeader(AUTHORIZATION);

        if (authHeader != null) {
            Matcher m = OAUTH_HEADER.matcher(authHeader);
            if (m.matches()) {
                if (AUTH_SCHEME.equalsIgnoreCase(m.group(1))) {
                    return m.group(2);
                }
            }
        }
        return null;
    }

}
