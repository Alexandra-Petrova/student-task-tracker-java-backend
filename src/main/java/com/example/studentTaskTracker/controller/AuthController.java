package com.example.studentTaskTracker.controller;

import com.example.studentTaskTracker.dto.request.RefreshTokenRequest;
import com.example.studentTaskTracker.dto.request.SignInRequest;
import com.example.studentTaskTracker.dto.request.SignUpRequest;
import com.example.studentTaskTracker.dto.response.JwtAuthenticationResponse;
import com.example.studentTaskTracker.dto.response.exception.AlreadyExistsException;
import com.example.studentTaskTracker.dto.response.exception.InvalidCredentialsException;
import com.example.studentTaskTracker.dto.response.exception.InvalidJwtException;

public interface AuthController {

    JwtAuthenticationResponse signUp(SignUpRequest signUpRequest) throws AlreadyExistsException;

    JwtAuthenticationResponse signIn(SignInRequest signInRequest) throws InvalidJwtException, InvalidCredentialsException;

    JwtAuthenticationResponse refreshAccessToken(RefreshTokenRequest refreshTokenRequest) throws InvalidJwtException;
}
