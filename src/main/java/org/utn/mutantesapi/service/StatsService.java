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

    private double calculateRatio(long mutantCount, long humanCount) {
        if (humanCount == 0) {
            return (mutantCount > 0) ? 1.0 : 0.0;
        }
        return (double) mutantCount / humanCount;
    }
}