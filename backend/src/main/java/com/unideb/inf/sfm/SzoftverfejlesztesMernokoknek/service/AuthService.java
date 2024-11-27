package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.security.AuthRequest;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.security.AuthResponse;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.security.RegisterRequest;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.User;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.UserRepository;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.security.jwt.JwtService;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils.AuthServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final AuthServiceUtils authServiceUtils;

    public AuthResponse register(RegisterRequest request) {
        User user = authServiceUtils.createUser(request);
        userRepository.save(user);
//        emailService.sendRegistrationEmail(user.getEmail());
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthResponse.builder().token(jwtToken).refreshToken(refreshToken).build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword())
        );
        var user = authServiceUtils.findUserByUsername(request.getUsername());
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthResponse.builder().token(jwtToken).refreshToken(refreshToken).build();
    }
}
