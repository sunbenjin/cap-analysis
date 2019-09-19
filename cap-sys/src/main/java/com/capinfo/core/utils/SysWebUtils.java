package com.capinfo.core.utils;

import com.capinfo.util.JsonUtil;
import com.capinfo.util.JsonUtils;
import com.capinfo.util.ReType;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SysWebUtils {

    public SysWebUtils(){

    }

    public static void writeJsonResponse(HttpServletResponse response, ReType reType) {

        response.setContentType("application/json");

        try {

            String json = JsonUtils.toJson(reType);

            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        } catch (IOException e) {
            throw new IllegalStateException("Write OAuthResponse error", e);
        }

    }

    /**
     * Retrieve client ip address
     *
     * @param request HttpServletRequest
     * @return IP
     */
    public static String retrieveClientIp(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-Real-IP");
        if (isUnAvailableIp(remoteAddr)) {
            remoteAddr = request.getHeader("X-Forwarded-For");
        }else if (isUnAvailableIp(remoteAddr)) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        }else if (isUnAvailableIp(remoteAddr)) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
    }

    private static boolean isUnAvailableIp(String ip) {
        return (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip));
    }
}
