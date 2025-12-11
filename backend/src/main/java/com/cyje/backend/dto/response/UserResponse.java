package com.cyje.backend.dto.response;

import com.cyje.backend.entity.User;
import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String prenom;
    private String nom;
    private User.Role role;
    private Boolean actif;
    private LocalDateTime createdAt;
}
