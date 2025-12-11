package com.cyje.backend.repository;

import com.cyje.backend.entity.Prospect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProspectRepository extends JpaRepository<Prospect, Long> {
    List<Prospect> findByStatut(Prospect.Statut statut);
    List<Prospect> findByCreatedById(Long userId);
    
    @Query("SELECT p FROM Prospect p WHERE " +
           "(:statut IS NULL OR p.statut = :statut) AND " +
           "(:secteur IS NULL OR p.secteurActivite = :secteur) AND " +
           "(:debut IS NULL OR p.createdAt >= :debut) AND " +
           "(:fin IS NULL OR p.createdAt <= :fin)")
    List<Prospect> findByFilters(
        @Param("statut") Prospect.Statut statut,
        @Param("secteur") String secteur,
        @Param("debut") LocalDateTime debut,
        @Param("fin") LocalDateTime fin
    );
    
    @Query("SELECT COUNT(p) FROM Prospect p WHERE p.statut = :statut")
    Long countByStatut(@Param("statut") Prospect.Statut statut);
    
    @Query("SELECT p.secteurActivite, COUNT(p) FROM Prospect p GROUP BY p.secteurActivite")
    List<Object[]> countBySecteur();
}
