package org.utn.mutantesapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.utn.mutantesapi.dto.StatsResponse;
import org.utn.mutantesapi.repository.DnaRecordRepository;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final DnaRecordRepository dnaRecordRepository;

    public StatsResponse getStats() {
        long countMutant = dnaRecordRepository.countByIsMutant(true);
        long countHuman = dnaRecordRepository.countByIsMutant(false);

        double ratio = calculateRatio(countMutant, countHuman);

        return new StatsResponse(countMutant, countHuman, ratio);
    }

    // Ejemplo de cómo deberías calcular el ratio para evitar división por cero
    private double calcularRatio(long mutantCount, long humanCount) {
        if (humanCount == 0) {
            // Opción A: Devolver 0.0 si no hay humanos para dividir (caso común en APIs)
            return 0.0;

            // Opción B: Si el profesor espera 1.0 cuando solo hay mutantes, cámbialo a:
            // return (mutantCount > 0) ? 1.0 : 0.0;
        }
        // Usamos Double para asegurar que la división no sea de enteros (que daría 0)
        return (double) mutantCount / humanCount;
    }
}