package com.example.studentTaskTracker.service;

import com.example.studentTaskTracker.dto.request.RefreshTokenRequest;
import com.example.studentTaskTracker.dto.request.SignInRequest;
import com.example.studentTaskTracker.dto.request.SignUpRequest;
import com.example.studentTaskTracker.dto.response.JwtAuthenticationResponse;
import com.example.studentTaskTracker.dto.response.exception.AlreadyExistsException;
import com.example.studentTaskTracker.dto.response.exception.InvalidCredentialsException;
import com.example.studentTaskTracker.dto.response.exception.InvalidJwtException;

public interface AuthenticationService {

    JwtAuthenticationResponse signUp(SignUpRequest request) throws AlreadyExistsException;

    JwtAuthenticationResponse signIn(SignInRequest request) throws InvalidJwtException, InvalidCredentialsException;

    JwtAuthenticationResponse refreshAccessToken(RefreshTokenRequest request) throws InvalidJwtException;

}
