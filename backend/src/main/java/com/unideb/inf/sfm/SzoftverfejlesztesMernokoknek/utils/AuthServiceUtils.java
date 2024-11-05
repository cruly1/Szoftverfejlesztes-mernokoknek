package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.security.RegisterRequest;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.customexceptions.ResourceNotFoundException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.User;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.enums.ERole;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceUtils {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User createUser(RegisterRequest request) {
        return User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(ERole.USER)
                .build();
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(1L, "User"));
    }
}
