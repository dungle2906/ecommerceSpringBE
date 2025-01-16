package com.example.securityApiJWT.Service.AuthenticationService;

import com.example.securityApiJWT.DTO.AuthenticationRequest;
import com.example.securityApiJWT.DTO.AuthenticationResponse;
import com.example.securityApiJWT.DTO.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IAuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
