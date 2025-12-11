package com.cyje.backend.service;

import com.cyje.backend.dto.request.ProspectRequest;
import com.cyje.backend.dto.response.ProspectResponse;
import com.cyje.backend.entity.Prospect;
import com.cyje.backend.entity.User;
import com.cyje.backend.exception.ResourceNotFoundException;
import com.cyje.backend.repository.ProspectRepository;
import com.cyje.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProspectService {

    private final ProspectRepository prospectRepository;
    private final UserRepository userRepository;

    public List<ProspectResponse> getAllProspects() {
        return prospectRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public ProspectResponse getProspectById(Long id) {
        Prospect prospect = prospectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prospect non trouvé avec l'ID: " + id));
        return convertToResponse(prospect);
    }

    public List<ProspectResponse> getProspectsByStatut(Prospect.Statut statut) {
        return prospectRepository.findByStatut(statut).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ProspectResponse> searchProspects(
            Prospect.Statut statut,
            String secteur,
            LocalDateTime debut,
            LocalDateTime fin) {
        return prospectRepository.findByFilters(statut, secteur, debut, fin).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProspectResponse createProspect(ProspectRequest request) {
        // Récupérer l'utilisateur connecté
        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        // Créer le prospect
        Prospect prospect = Prospect.builder()
                .nomEntreprise(request.getNomEntreprise())
                .nomContact(request.getNomContact())
                .email(request.getEmail())
                .telephone(request.getTelephone())
                .statut(request.getStatut())
                .montantPotentiel(request.getMontantPotentiel())
                .secteurActivite(request.getSecteurActivite())
                .notes(request.getNotes())
                .createdBy(user)
                .build();

        prospect = prospectRepository.save(prospect);
        return convertToResponse(prospect);
    }

    @Transactional
    public ProspectResponse updateProspect(Long id, ProspectRequest request) {
        Prospect prospect = prospectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prospect non trouvé avec l'ID: " + id));

        prospect.setNomEntreprise(request.getNomEntreprise());
        prospect.setNomContact(request.getNomContact());
        prospect.setEmail(request.getEmail());
        prospect.setTelephone(request.getTelephone());
        prospect.setStatut(request.getStatut());
        prospect.setMontantPotentiel(request.getMontantPotentiel());
        prospect.setSecteurActivite(request.getSecteurActivite());
        prospect.setNotes(request.getNotes());

        prospect = prospectRepository.save(prospect);
        return convertToResponse(prospect);
    }

    @Transactional
    public void deleteProspect(Long id) {
        if (!prospectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Prospect non trouvé avec l'ID: " + id);
        }
        prospectRepository.deleteById(id);
    }

    private ProspectResponse convertToResponse(Prospect prospect) {
        return ProspectResponse.builder()
                .id(prospect.getId())
                .nomEntreprise(prospect.getNomEntreprise())
                .nomContact(prospect.getNomContact())
                .email(prospect.getEmail())
                .telephone(prospect.getTelephone())
                .statut(prospect.getStatut())
                .montantPotentiel(prospect.getMontantPotentiel())
                .secteurActivite(prospect.getSecteurActivite())
                .notes(prospect.getNotes())
                .createdByName(prospect.getCreatedBy().getPrenom() + " " + prospect.getCreatedBy().getNom())
                .createdById(prospect.getCreatedBy().getId())
                .createdAt(prospect.getCreatedAt())
                .updatedAt(prospect.getUpdatedAt())
                .build();
    }

    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }
}