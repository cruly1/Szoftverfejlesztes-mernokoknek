package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieService {

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    public void setTokenCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) jwtExpiration);
        response.addCookie(cookie);
    }
}
