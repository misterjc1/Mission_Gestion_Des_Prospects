package com.cyje.backend.service;

import com.cyje.backend.dto.response.StatsResponse;
import com.cyje.backend.entity.Prospect;
import com.cyje.backend.repository.ProspectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final ProspectRepository prospectRepository;

    public StatsResponse getStats() {
        // Compter le total de prospects
        long totalProspects = prospectRepository.count();

        // Compter par statut
        long nouveaux = prospectRepository.countByStatut(Prospect.Statut.NOUVEAU);
        long contactes = prospectRepository.countByStatut(Prospect.Statut.CONTACTE);
        long relances = prospectRepository.countByStatut(Prospect.Statut.RELANCE);
        long signes = prospectRepository.countByStatut(Prospect.Statut.SIGNE);
        long perdus = prospectRepository.countByStatut(Prospect.Statut.PERDU);

        // Calculer le montant total des prospects signés
        BigDecimal montantTotalSigne = prospectRepository.findByStatut(Prospect.Statut.SIGNE)
                .stream()
                .map(Prospect::getMontantPotentiel)
                .filter(montant -> montant != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculer le taux de conversion
        double tauxConversion = totalProspects > 0
                ? (double) signes / totalProspects * 100
                : 0.0;

        // Compter par secteur d'activité
        Map<String, Long> parSecteur = new HashMap<>();
        List<Object[]> secteurCounts = prospectRepository.countBySecteur();
        for (Object[] result : secteurCounts) {
            String secteur = (String) result[0];
            Long count = (Long) result[1];
            if (secteur != null) {
                parSecteur.put(secteur, count);
            }
        }

        return StatsResponse.builder()
                .totalProspects(totalProspects)
                .nouveaux(nouveaux)
                .contactes(contactes)
                .relances(relances)
                .signes(signes)
                .perdus(perdus)
                .montantTotalSigne(montantTotalSigne)
                .tauxConversion(Math.round(tauxConversion * 100.0) / 100.0)
                .parSecteur(parSecteur)
                .build();
    }
}