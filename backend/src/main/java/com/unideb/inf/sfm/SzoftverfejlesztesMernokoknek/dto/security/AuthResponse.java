package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
}
