package com.example.Backend.service.AuthService;

import java.util.Set;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Backend.dto.auth.AuthResponseV1;
import com.example.Backend.dto.auth.LoginRequestV1;
import com.example.Backend.dto.auth.RegistrationRequestV1;
import com.example.Backend.entity.UserModel;
import com.example.Backend.exception.UserAlreadyExistException.UserAlreadyExistException;
import com.example.Backend.repository.jpa.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceV1 {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponseV1 registerUser(RegistrationRequestV1 request){
        // check if the user with similar email exists
        if(userRepository.existsByEmail(request.getEmail())){
            throw new UserAlreadyExistException("User with this email already exists!");
        }
    UserModel user = new UserModel();
    user.setEmail(request.getEmail());
    user.setFullName(request.getFullName());

        // secure password before writing to db
    user.setPassword(passwordEncoder.encode(request.getPassword()));

        /// use set data structure to get the right role 
    user.setRoles(Set.of(request.getRole()));

    userRepository.save(user);
    
    String token = jwtService.generateToken(user);
    return AuthResponseV1.builder()
                          .fullName(user.getFullName())
                          .email(user.getEmail())
                          .roles(user.getRoles())
                          .token(token)
                          .build();
}
    
    public AuthResponseV1 login(LoginRequestV1 request){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserModel user = userRepository.findByEmail(request.getEmail())
                                 .orElseThrow(()-> new UsernameNotFoundException("User not found: "));

        String token = jwtService.generateToken(user);
        return AuthResponseV1.builder()
                             .fullName(user.getFullName())
                             .email(user.getEmail())
                             .roles(user.getRoles())
                             .token(token)
                             .build();
    }
}
