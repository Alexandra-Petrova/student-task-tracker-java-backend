package com.example.studentTaskTracker.controller;

import com.example.studentTaskTracker.dto.request.RefreshTokenRequest;
import com.example.studentTaskTracker.dto.request.SignInRequest;
import com.example.studentTaskTracker.dto.request.SignUpRequest;
import com.example.studentTaskTracker.dto.response.JwtAuthenticationResponse;
import com.example.studentTaskTracker.dto.response.exception.AlreadyExistsException;
import com.example.studentTaskTracker.dto.response.exception.InvalidCredentialsException;
import com.example.studentTaskTracker.dto.response.exception.InvalidJwtException;
import com.example.studentTaskTracker.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthControllerImpl implements AuthController {

    private final AuthenticationService authenticationService;

    public AuthControllerImpl(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest) throws AlreadyExistsException {
        return authenticationService.signUp(signUpRequest);
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody SignInRequest signInRequest) throws InvalidJwtException, InvalidCredentialsException {
        return authenticationService.signIn(signInRequest);
    }

    @PostMapping("/refresh")
    public JwtAuthenticationResponse refreshAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) throws InvalidJwtException {
        return authenticationService.refreshAccessToken(refreshTokenRequest);
    }
}
