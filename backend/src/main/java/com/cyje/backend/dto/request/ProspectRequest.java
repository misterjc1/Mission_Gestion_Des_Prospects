package com.cyje.backend.dto.request;

import com.cyje.backend.entity.Prospect;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProspectRequest {
    @NotBlank(message = "Le nom de l'entreprise est obligatoire")
    private String nomEntreprise;
    
    @NotBlank(message = "Le nom du contact est obligatoire")
    private String nomContact;
    
    @Email(message = "Format d'email invalide")
    private String email;
    
    private String telephone;
    private Prospect.Statut statut = Prospect.Statut.NOUVEAU;
    private BigDecimal montantPotentiel;
    private String secteurActivite;
    private String notes;
}
