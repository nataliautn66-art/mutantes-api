package org.utn.mutantesapi.service;

import org.springframework.stereotype.Service;
import org.utn.mutantesapi.dto.StatsResponse;
import org.utn.mutantesapi.entity.DnaRecord;
import org.utn.mutantesapi.exception.InvalidDnaException;
import org.utn.mutantesapi.repository.DnaRecordRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Service
public class MutantService {

    private final DnaRecordRepository repository;

    public MutantService(DnaRecordRepository repository) {
        this.repository = repository;
    }

    public boolean isMutant(String[] dna) {
        validateDna(dna);

        // Calcular hash del DNA
        String dnaHash = calculateHash(dna);

        // Verificar si ya existe en la base de datos
        DnaRecord existingRecord = repository.findByDnaHash(dnaHash);

        if (existingRecord != null) {
            return existingRecord.getIsMutant();
        }

        // Calcular si es mutante
        boolean isMutant = checkMutant(dna);

        // Guardar en la base de datos
        DnaRecord record = new DnaRecord(null, dnaHash, isMutant, LocalDateTime.now());
        repository.save(record);

        return isMutant;
    }
    private void validateDna(String[] dna) {
        if (dna == null || dna.length == 0) {
            throw new InvalidDnaException("DNA array cannot be null or empty");
        }

        int n = dna.length;
        if (n < 4) {
            throw new InvalidDnaException("DNA matrix must be at least 4x4");
        }

        for (String sequence : dna) {
            if (sequence == null || sequence.length() != n) {
                throw new InvalidDnaException("DNA matrix must be square (NxN)");
            }

            if (!sequence.matches("[ATCG]+")) {
                throw new InvalidDnaException("Invalid character in DNA sequence");
            }
        }
    }

    private String calculateHash(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String dnaString = String.join(",", dna);
            byte[] hash = digest.digest(dnaString.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error calculating DNA hash", e);
        }
    }

    private boolean checkMutant(String[] dna) {
        int n = dna.length;
        int sequenceCount = 0;

        // Buscar horizontalmente
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= n - 4; j++) {
                if (hasSequence(dna[i].charAt(j), dna[i].charAt(j + 1),
                        dna[i].charAt(j + 2), dna[i].charAt(j + 3))) {
                    sequenceCount++;
                    if (sequenceCount > 1) return true;
                }
            }
        }

        // Buscar verticalmente
        for (int i = 0; i <= n - 4; i++) {
            for (int j = 0; j < n; j++) {
                if (hasSequence(dna[i].charAt(j), dna[i + 1].charAt(j),
                        dna[i + 2].charAt(j), dna[i + 3].charAt(j))) {
                    sequenceCount++;
                    if (sequenceCount > 1) return true;
                }
            }
        }

        // Buscar diagonalmente (\)
        for (int i = 0; i <= n - 4; i++) {
            for (int j = 0; j <= n - 4; j++) {
                if (hasSequence(dna[i].charAt(j), dna[i + 1].charAt(j + 1),
                        dna[i + 2].charAt(j + 2), dna[i + 3].charAt(j + 3))) {
                    sequenceCount++;
                    if (sequenceCount > 1) return true;
                }
            }
        }

        // Buscar diagonalmente (/)
        for (int i = 0; i <= n - 4; i++) {
            for (int j = 3; j < n; j++) {
                if (hasSequence(dna[i].charAt(j), dna[i + 1].charAt(j - 1),
                        dna[i + 2].charAt(j - 2), dna[i + 3].charAt(j - 3))) {
                    sequenceCount++;
                    if (sequenceCount > 1) return true;
                }
            }
        }

        return false;
    }

    private boolean hasSequence(char c1, char c2, char c3, char c4) {
        return c1 == c2 && c2 == c3 && c3 == c4;
    }

    public StatsResponse getStats() {
        long countMutantDna = repository.countByIsMutant(true);
        long countHumanDna = repository.countByIsMutant(false);

        double ratio = countHumanDna == 0 ? 0.0 : (double) countMutantDna / countHumanDna;

        return new StatsResponse(countMutantDna, countHumanDna, ratio);
    }
}