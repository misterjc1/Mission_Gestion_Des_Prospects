package com.cyje.backend.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.util.Map;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class StatsResponse {
    private Long totalProspects;
    private Long nouveaux;
    private Long contactes;
    private Long relances;
    private Long signes;
    private Long perdus;
    private BigDecimal montantTotalSigne;
    private Double tauxConversion;
    private Map<String, Long> parSecteur;
}
