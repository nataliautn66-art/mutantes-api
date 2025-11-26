package org.utn.mutantesapi.service;

import org.springframework.stereotype.Component;

/**
 * Detector de mutantes basado en secuencias de ADN.
 * Un humano es mutante si encuentra MÁS DE UNA secuencia de 4 letras iguales
 * en cualquier dirección: horizontal, vertical o diagonal.
 */
@Component
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;

    /**
     * Verifica si un ADN corresponde a un mutante.
     *
     * @param dna Array de Strings que representa la matriz de ADN (NxN)
     * @return true si es mutante (más de 1 secuencia), false si no
     */
    public boolean isMutant(String[] dna) {
        if (dna == null || dna.length == 0) {
            return false;
        }

        final int n = dna.length;

        // Convertir a matriz de chars para acceso más rápido
        char[][] matrix = new char[n][];
        for (int i = 0; i < n; i++) {
            if (dna[i] == null || dna[i].length() != n) {
                return false;
            }
            matrix[i] = dna[i].toCharArray();
        }

        int sequenceCount = 0;

        // Recorrer cada posición de la matriz
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                // Early termination: si ya encontramos 2+ secuencias, es mutante
                if (sequenceCount > 1) {
                    return true;
                }

                // Buscar en las 4 direcciones (sin else if para no perder secuencias)

                // Horizontal →
                if (col <= n - SEQUENCE_LENGTH) {
                    if (checkHorizontal(matrix, row, col)) {
                        sequenceCount++;
                    }
                }

                // Vertical ↓
                if (row <= n - SEQUENCE_LENGTH) {
                    if (checkVertical(matrix, row, col)) {
                        sequenceCount++;
                    }
                }

                // Diagonal descendente ↘
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalDescending(matrix, row, col)) {
                        sequenceCount++;
                    }
                }

                // Diagonal ascendente ↗
                if (row >= SEQUENCE_LENGTH - 1 && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalAscending(matrix, row, col)) {
                        sequenceCount++;
                    }
                }
            }
        }

        return sequenceCount > 1;
    }

    /**
     * Verifica secuencia horizontal desde (row, col)
     */
    private boolean checkHorizontal(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row][col + 1] == base &&
                matrix[row][col + 2] == base &&
                matrix[row][col + 3] == base;
    }

    /**
     * Verifica secuencia vertical desde (row, col)
     */
    private boolean checkVertical(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row + 1][col] == base &&
                matrix[row + 2][col] == base &&
                matrix[row + 3][col] == base;
    }

    /**
     * Verifica secuencia diagonal descendente ↘ desde (row, col)
     */
    private boolean checkDiagonalDescending(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row + 1][col + 1] == base &&
                matrix[row + 2][col + 2] == base &&
                matrix[row + 3][col + 3] == base;
    }

    /**
     * Verifica secuencia diagonal ascendente ↗ desde (row, col)
     */
    private boolean checkDiagonalAscending(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row - 1][col + 1] == base &&
                matrix[row - 2][col + 2] == base &&
                matrix[row - 3][col + 3] == base;
    }
}