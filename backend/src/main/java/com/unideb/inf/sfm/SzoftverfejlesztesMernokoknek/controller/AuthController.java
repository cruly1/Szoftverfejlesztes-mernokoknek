package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.controller;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.security.AuthRequest;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.security.AuthResponse;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.security.RegisterRequest;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.security.jwt.JwtService;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service.AuthService;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service.CookieService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final CookieService cookieService;
    private final UserDetailsService userDetailsService;

    @PostMapping("register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request, HttpServletResponse response) {
        AuthResponse authResponse = authService.authenticate(request);
        cookieService.setTokenCookie(response, authResponse.getToken());
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Token");
        }

        String token = authHeader.substring(7);
        jwtService.invalidateToken(token);

        return ResponseEntity.ok("Logout succesful.");
    }

    @PostMapping("refreshToken")
    public ResponseEntity<?> refreshAccessToken(@RequestHeader("Authorization") String refreshToken) {
        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Refresh Token");
        }

        String token = refreshToken.substring(7);
        if (jwtService.isTokenInvalidated(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token has been invalidated.");
        }

        if (jwtService.isTokenExpired(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token has expired.");
        }

        String username = jwtService.extractUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String newAccessToken = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }
}
