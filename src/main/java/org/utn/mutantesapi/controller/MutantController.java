package org.utn.mutantesapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.utn.mutantesapi.dto.DnaRequest;
import org.utn.mutantesapi.dto.StatsResponse;
import org.utn.mutantesapi.service.MutantService;
import org.utn.mutantesapi.service.StatsService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    @PostMapping("/mutant")
    public ResponseEntity<Void> checkMutant(@Valid @RequestBody DnaRequest request) {
        log.info("Analizando secuencia de ADN");

        boolean isMutant = mutantService.analyzeDna(request.getDna());

        if (isMutant) {
            log.info("El ADN es de un mutante ✓");
            return ResponseEntity.ok().build();
        } else {
            log.info("El ADN es de un humano");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats() {
        log.info("Obteniendo estadísticas de verificaciones de ADN");
        StatsResponse stats = statsService.getStats();
        return ResponseEntity.ok(stats);
    }
}
