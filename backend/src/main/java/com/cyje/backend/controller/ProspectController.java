package com.cyje.backend.controller;

import com.cyje.backend.dto.request.ProspectRequest;
import com.cyje.backend.dto.response.ProspectResponse;
import com.cyje.backend.entity.Prospect;
import com.cyje.backend.service.ProspectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/prospects")
@RequiredArgsConstructor
public class ProspectController {

    private final ProspectService prospectService;

    @GetMapping
    public ResponseEntity<List<ProspectResponse>> getAllProspects() {
        return ResponseEntity.ok(prospectService.getAllProspects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProspectResponse> getProspectById(@PathVariable Long id) {
        return ResponseEntity.ok(prospectService.getProspectById(id));
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<ProspectResponse>> getProspectsByStatut(@PathVariable Prospect.Statut statut) {
        return ResponseEntity.ok(prospectService.getProspectsByStatut(statut));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProspectResponse>> searchProspects(
            @RequestParam(required = false) Prospect.Statut statut,
            @RequestParam(required = false) String secteur,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime debut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(prospectService.searchProspects(statut, secteur, debut, fin));
    }

    @PostMapping
    public ResponseEntity<ProspectResponse> createProspect(@Valid @RequestBody ProspectRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(prospectService.createProspect(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProspectResponse> updateProspect(
            @PathVariable Long id,
            @Valid @RequestBody ProspectRequest request) {
        return ResponseEntity.ok(prospectService.updateProspect(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProspect(@PathVariable Long id) {
        prospectService.deleteProspect(id);
        return ResponseEntity.noContent().build();
    }
}