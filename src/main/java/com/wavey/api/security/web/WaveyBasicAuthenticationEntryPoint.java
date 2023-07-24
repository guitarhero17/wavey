package com.wavey.api.security.web;

import com.google.gson.Gson;
import com.wavey.api.security.data.UnauthorizedAccessEntry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class WaveyBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException {
        response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        UnauthorizedAccessEntry entry = new UnauthorizedAccessEntry(authEx.getMessage());
        String errorJson = new Gson().toJson(entry);

        PrintWriter out = response.getWriter();

        out.write(errorJson);
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("Wavey");
        super.afterPropertiesSet();
    }
}
