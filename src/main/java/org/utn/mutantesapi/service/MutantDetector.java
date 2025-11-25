package org.utn.mutantesapi.service;

import org.springframework.stereotype.Component;

@Component
public class MutantDetector {

    public boolean isMutant(String[] dna) {  // ← Corregido: isHWtant → isMutant
        int n = dna.length;
        int sequenceCount = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Si ya encontramos más de una secuencia, retornar inmediatamente
                if (sequenceCount > 1) return true;

                // Verificar horizontal (hacia la derecha)
                if (j <= n - 4) {
                    if (checkSequence(dna, i, j, 0, 1)) {
                        sequenceCount++;
                        continue; // Continuar para no contar duplicados
                    }
                }

                // Verificar vertical (hacia abajo)
                if (i <= n - 4) {
                    if (checkSequence(dna, i, j, 1, 0)) {
                        sequenceCount++;
                        continue;
                    }
                }

                // Verificar diagonal principal (abajo-derecha)
                if (i <= n - 4 && j <= n - 4) {
                    if (checkSequence(dna, i, j, 1, 1)) {
                        sequenceCount++;
                        continue;
                    }
                }

                // Verificar diagonal secundaria (abajo-izquierda)
                if (i <= n - 4 && j >= 3) {
                    if (checkSequence(dna, i, j, 1, -1)) {
                        sequenceCount++;
                    }
                }
            }
        }

        return sequenceCount > 1;
    }

    private boolean checkSequence(String[] dna, int row, int col, int rowStep, int colStep) {
        char firstChar = dna[row].charAt(col);

        for (int k = 1; k < 4; k++) {
            int currentRow = row + k * rowStep;
            int currentCol = col + k * colStep;

            if (dna[currentRow].charAt(currentCol) != firstChar) {
                return false;
            }
        }
        return true;
    }
}