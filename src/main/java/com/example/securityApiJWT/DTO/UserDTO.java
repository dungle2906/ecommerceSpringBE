package com.example.securityApiJWT.DTO;

import com.example.securityApiJWT.Model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private boolean gender;
    private String about;
    private String imageName;

    @Enumerated(EnumType.STRING)
    private Role role;
}
