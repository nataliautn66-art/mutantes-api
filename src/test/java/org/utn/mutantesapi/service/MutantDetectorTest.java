package org.utn.mutantesapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

    private MutantDetector mutantDetector;

    @BeforeEach
    void setUp() {
        mutantDetector = new MutantDetector();
    }

    @Test
    void testMutantDna_HorizontalSequence() {
        String[] dna = {
                "AAAACG",
                "TTTTGC",
                "ATCGAT",
                "ATCGAT",
                "ATCGAT",
                "ATCGAT"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    void testMutantDna_VerticalSequence() {
        String[] dna = {
                "ATCGAT",
                "ATCGAT",
                "ATCGAT",
                "ATCGAT",
                "ATCGAT",
                "ATCGAT"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    void testMutantDna_DiagonalSequence() {
        String[] dna = {
                "ATGCTA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    void testHumanDna_NoSequences() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    void testHumanDna_OnlyOneSequence() {
        String[] dna = {
                "AAAACG",
                "TAGCGC",
                "TTATTT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    void testExampleFromRequirements_Mutant() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }
}