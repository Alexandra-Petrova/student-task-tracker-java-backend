package com.example.studentTaskTracker.service;

import com.example.studentTaskTracker.dto.request.RefreshTokenRequest;
import com.example.studentTaskTracker.dto.request.SignInRequest;
import com.example.studentTaskTracker.dto.request.SignUpRequest;
import com.example.studentTaskTracker.dto.response.JwtAuthenticationResponse;
import com.example.studentTaskTracker.dto.response.exception.AlreadyExistsException;
import com.example.studentTaskTracker.dto.response.exception.InvalidCredentialsException;
import com.example.studentTaskTracker.dto.response.exception.InvalidJwtException;
import com.example.studentTaskTracker.entity.RefreshToken;
import com.example.studentTaskTracker.entity.Role;
import com.example.studentTaskTracker.entity.User;
import com.example.studentTaskTracker.repository.RefreshTokenRepository;
import com.example.studentTaskTracker.security.config.JwtConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtConfig jwtConfig;

    public AuthenticationServiceImpl(
            UserService userService,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            RefreshTokenRepository refreshTokenRepository,
            JwtConfig jwtConfig
    ) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtConfig = jwtConfig;
    }

    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest request) throws AlreadyExistsException {
        User user = new User(
                request.firstName(),
                request.lastName(),
                request.email(),
                passwordEncoder.encode(request.password()),
                Role.ROLE_USER
        );

        userService.create(user);

        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user);

        refreshTokenRepository.save(new RefreshToken(refreshToken, user.getUsername()));

        return new JwtAuthenticationResponse(accessToken, refreshToken);
    }

    @Override
    public JwtAuthenticationResponse refreshAccessToken(RefreshTokenRequest refreshTokenRequest) throws InvalidJwtException {
        String token = refreshTokenRequest.token();
        String extractedUsername = jwtService.extractUsername(token);
        if (extractedUsername == null) {
            throw new InvalidJwtException();
        }

        UserDetails currentUserDetails = userDetailsService.loadUserByUsername(extractedUsername);
        RefreshToken refreshToken = refreshTokenRepository.findByValue(token);
        if (refreshToken == null || !jwtService.isTokenValid(token, currentUserDetails) ||
                !currentUserDetails.getUsername().equals(refreshToken.getOwnerUsername())) {
            throw new InvalidJwtException();
        }

        String newAccessToken = generateAccessToken(currentUserDetails);
        return new JwtAuthenticationResponse(newAccessToken, refreshToken.getValue());
    }

    private String generateAccessToken(UserDetails user) {
        return jwtService.generateToken(user, new Date(System.currentTimeMillis() + jwtConfig.getAccessTokenExpiration()));
    }

    private String generateRefreshToken(UserDetails user) {
        return jwtService.generateToken(user, new Date(System.currentTimeMillis() + jwtConfig.getRefreshTokenExpiration()));
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) throws InvalidJwtException, InvalidCredentialsException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException();
        }

        UserDetails user = userDetailsService.loadUserByUsername(request.email());

        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user);

        RefreshToken oldToken = refreshTokenRepository.findByOwnerUsername(user.getUsername());
        if (oldToken == null) {
//            throw new InvalidJwtException();
            oldToken = new RefreshToken(refreshToken, user.getUsername());
        } else {
            oldToken.setValue(refreshToken);
        }
        refreshTokenRepository.save(oldToken);

        return new JwtAuthenticationResponse(accessToken, refreshToken);


//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.email(), request.password())
//        );
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());
//        String accessToken = generateAccessToken(userDetails);
//        String refreshToken = generateRefreshToken(userDetails);
//
//        // Всегда сохраняем/перезаписываем refresh token
//        refreshTokenRepository.upsert(refreshToken, userDetails.getUsername());
//        // или если нет upsert — вот так:
//        RefreshToken token = refreshTokenRepository
//                .findByOwnerUsername(userDetails.getUsername())
//                .orElse(new RefreshToken());
//        token.setValue(refreshToken);
//        token.setOwnerUsername(userDetails.getUsername());
//        refreshTokenRepository.save(token);
//
//        return new JwtAuthenticationResponse(accessToken, refreshToken);
    }
}
