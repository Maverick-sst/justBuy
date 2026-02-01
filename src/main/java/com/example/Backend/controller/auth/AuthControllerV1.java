package com.example.Backend.controller.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Backend.dto.auth.AuthResponseV1;
import com.example.Backend.dto.auth.LoginRequestV1;
import com.example.Backend.dto.auth.RegistrationRequestV1;
import com.example.Backend.service.AuthService.AuthServiceV1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthControllerV1 {
   
    @Autowired
    public AuthServiceV1 authServiceV1;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseV1> register(@Valid @RequestBody RegistrationRequestV1 request) {
        AuthResponseV1 response = authServiceV1.registerUser(request);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
       
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponseV1> login(@Valid @RequestBody LoginRequestV1 request) {
        AuthResponseV1 response = authServiceV1.login(request);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    
    
}
