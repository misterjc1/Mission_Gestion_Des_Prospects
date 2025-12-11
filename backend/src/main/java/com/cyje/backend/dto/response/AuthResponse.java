package com.cyje.backend.dto.response;

import com.cyje.backend.entity.User;
import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private String prenom;
    private String nom;
    private User.Role role;
}
