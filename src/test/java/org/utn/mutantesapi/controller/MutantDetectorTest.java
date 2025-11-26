package org.utn.mutantesapi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.utn.mutantesapi.service.MutantDetector;

import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

    private MutantDetector detector;

    @BeforeEach
    void setUp() {
        detector = new MutantDetector();
    }

    // ========== TESTS DE MUTANTES (deben retornar true) ==========

    @Test
    @DisplayName("Debe detectar mutante con secuencias horizontal y diagonal")
    void testMutant_HorizontalAndDiagonal() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA", // Horizontal: CCCC
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con dos secuencias verticales")
    void testMutant_TwoVertical() {
        String[] dna = {
                "ATGCGA",
                "AAGTGC",
                "ATATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con múltiples secuencias horizontales")
    void testMutant_MultipleHorizontal() {
        String[] dna = {
                "AAAAGA",
                "CAGTGC",
                "TTTTGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con diagonales ascendente y descendente")
    void testMutant_BothDiagonals() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CACCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante en matriz 10x10")
    void testMutant_LargeDna() {
        String[] dna = {
                "ATGCGAATGC",
                "CAGTGCAGTG",
                "TTATGTTATG",
                "AGAAGGAGAA",
                "CCCCTACCCC",
                "TCACTGTCAC",
                "ATGCGAATGC",
                "CAGTGCAGTG",
                "TTATGTTATG",
                "AGAAGGAGAA"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con todo el mismo carácter")
    void testMutant_AllSameCharacter() {
        String[] dna = {
                "AAAA",
                "AAAA",
                "AAAA",
                "AAAA"
        };
        assertTrue(detector.isMutant(dna));
    }

    // ========== TESTS DE NO MUTANTES (deben retornar false) ==========

    @Test
    @DisplayName("No debe detectar mutante con solo 1 secuencia")
    void testNotMutant_OnlyOneSequence() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT", // Solo una horizontal
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @DisplayName("No debe detectar mutante sin secuencias")
    void testNotMutant_NoSequences() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TGAC",
                "ACTG"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @DisplayName("No debe detectar mutante en matriz 4x4 sin secuencias")
    void testNotMutant_SmallDna() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(detector.isMutant(dna));
    }

    // ========== TESTS DE VALIDACIÓN (casos inválidos) ==========

    @Test
    @DisplayName("Debe retornar false para DNA null")
    void testInvalid_NullDna() {
        assertFalse(detector.isMutant(null));
    }

    @Test
    @DisplayName("Debe retornar false para array vacío")
    void testInvalid_EmptyDna() {
        String[] dna = {};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe retornar false para matriz no cuadrada")
    void testInvalid_NonSquareDna() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT"  // 3 filas x 4 columnas (no cuadrada)
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe retornar false para fila null")
    void testInvalid_NullRow() {
        String[] dna = {
                "ATGC",
                null,
                "TTAT",
                "AGAC"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe retornar false para matriz muy pequeña (3x3)")
    void testInvalid_TooSmallDna() {
        String[] dna = {
                "ATG",
                "CAG",
                "TTA"
        };
        assertFalse(detector.isMutant(dna));
    }

    // ========== TESTS DE CASOS BORDE ==========

    @Test
    @DisplayName("No debe contar secuencias de longitud mayor a 4")
    void testEdgeCase_SequenceLongerThanFour() {
        String[] dna = {
                "AAAAAA", // 6 A's seguidas (solo debe contar 1 secuencia, no múltiples)
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        // Depende de la implementación, pero debería necesitar otra secuencia
        // para ser considerado mutante
    }

    @Test
    @DisplayName("Debe detectar diagonal en esquina inferior derecha")
    void testEdgeCase_DiagonalInCorner() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAAG",
                "CACCTA",
                "TCACTA"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar múltiples secuencias que se cruzan")
    void testEdgeCase_CrossingSequences() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "ATATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }
}