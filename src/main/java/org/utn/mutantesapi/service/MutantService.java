package org.utn.mutantesapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.utn.mutantesapi.service.MutantDetector;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.utn.mutantesapi.entity.DnaRecord;
import org.utn.mutantesapi.repository.DnaRecordRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Slf4j
public class MutantService {

    private final MutantDetector mutantDetector;
    private final DnaRecordRepository dnaRecordRepository;

    @Transactional
    public boolean analyzeDna(String[] dna) {
        String dnaHash = calculateDnaHash(dna);

        Optional<DnaRecord> existingRecord = dnaRecordRepository.findByDnaHash(dnaHash);

        if (existingRecord.isPresent()) {
            log.info("ADN ya analizado, retornando resultado en caché");
            return existingRecord.get().getIsMutant();
        }

        boolean isMutant = mutantDetector.isMutant(dna);

        DnaRecord record = new DnaRecord(dnaHash, isMutant);
        dnaRecordRepository.save(record);

        log.info("ADN analizado y guardado: esMutante={}", isMutant);

        return isMutant;
    }

    private String calculateDnaHash(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String dnaString = String.join("", dna);
            byte[] hashBytes = digest.digest(dnaString.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            log.error("Error al calcular el hash del ADN", e);
            throw new RuntimeException("Error al calcular el hash del ADN", e);
        }
    }
}
