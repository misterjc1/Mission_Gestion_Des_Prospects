package com.cyje.backend.config;

import com.cyje.backend.entity.User;
import com.cyje.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Vérifier si un utilisateur admin existe déjà
        if (userRepository.findByEmail("admin@cyje.fr").isEmpty()) {

            // Créer un utilisateur admin par défaut
            User admin = User.builder()
                    .email("admin@cyje.fr")
                    .password(passwordEncoder.encode("admin123"))
                    .prenom("Admin")
                    .nom("CYJE")
                    .role(User.Role.ADMIN)
                    .actif(true)
                    .build();

            userRepository.save(admin);

            log.info("========================================");
            log.info("Utilisateur admin créé avec succès !");
            log.info("Email: admin@cyje.fr");
            log.info("Mot de passe: admin123");
            log.info("========================================");
        }
    }
}