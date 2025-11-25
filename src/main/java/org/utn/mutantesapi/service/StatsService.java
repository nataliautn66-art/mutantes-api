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

    private double calculateRatio(long countMutant, long countHuman) {
        if (countHuman == 0) {
            return countMutant > 0 ? (double) countMutant : 0.0;
        }
        return (double) countMutant / countHuman;
    }
}