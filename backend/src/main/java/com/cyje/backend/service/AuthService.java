package com.cyje.backend.service;

import com.cyje.backend.dto.request.LoginRequest;
import com.cyje.backend.dto.request.RegisterRequest;
import com.cyje.backend.dto.response.AuthResponse;
import com.cyje.backend.entity.User;
import com.cyje.backend.exception.BadRequestException;
import com.cyje.backend.repository.UserRepository;
import com.cyje.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Cet email est déjà utilisé");
        }

        // Créer le nouvel utilisateur
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .prenom(request.getPrenom())
                .nom(request.getNom())
                .role(request.getRole())
                .actif(request.getActif())
                .build();

        user = userRepository.save(user);

        // Générer le token JWT
        String token = jwtService.generateToken(user);

        // Construire la réponse
        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .id(user.getId())
                .email(user.getEmail())
                .prenom(user.getPrenom())
                .nom(user.getNom())
                .role(user.getRole())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        // Authentifier l'utilisateur
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Récupérer l'utilisateur
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Email ou mot de passe incorrect"));

        // Vérifier que le compte est actif
        if (!user.getActif()) {
            throw new BadRequestException("Votre compte a été désactivé");
        }

        // Générer le token JWT
        String token = jwtService.generateToken(user);

        // Construire la réponse
        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .id(user.getId())
                .email(user.getEmail())
                .prenom(user.getPrenom())
                .nom(user.getNom())
                .role(user.getRole())
                .build();
    }
}