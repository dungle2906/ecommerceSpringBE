package com.example.securityApiJWT.Controller;

import com.example.securityApiJWT.DTO.AuthenticationRequest;
import com.example.securityApiJWT.DTO.AuthenticationResponse;
import com.example.securityApiJWT.DTO.RegisterRequest;
import com.example.securityApiJWT.Service.AuthenticationService.IAuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    // 1. Handle the HTTP request for registration
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
           @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    // 8. Authenticates the user (verifies their email and password). Generates and sends a new JWT token for further requests.
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refreshToken")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }

//    @PostMapping("/google")
//    public ResponseEntity<AuthenticationResponse> loginWithGoogle(
//            @RequestBody Map<String, Object> dataObject
//    ) throws IOException {
//
//    };
}
