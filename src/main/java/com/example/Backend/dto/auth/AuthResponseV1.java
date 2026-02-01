package com.example.Backend.dto.auth;

import java.util.Set;

import com.example.Backend.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponseV1 {
    private String fullName;
    private String email;
    private Set<UserRole> roles;
    private String token ;

}
