package com.example.studentTaskTracker.dto.response;

import lombok.Getter;

@Getter
public class JwtAuthenticationResponse extends AbstractApiMessage{
    public JwtAuthenticationResponse(String accessToken, String refreshToken, Long userId) {
        super("Successfully authenticated.");
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
    }

    private final String accessToken;

    private final String refreshToken;

    private final Long userId;
}
