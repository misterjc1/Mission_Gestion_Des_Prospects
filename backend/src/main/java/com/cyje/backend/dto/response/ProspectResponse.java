package com.cyje.backend.dto.response;

import com.cyje.backend.entity.Prospect;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ProspectResponse {
    private Long id;
    private String nomEntreprise;
    private String nomContact;
    private String email;
    private String telephone;
    private Prospect.Statut statut;
    private BigDecimal montantPotentiel;
    private String secteurActivite;
    private String notes;
    private String createdByName;
    private Long createdById;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
