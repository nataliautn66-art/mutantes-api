package org.utn.mutantesapi.service;

import org.springframework.stereotype.Component;

@Component
public class DnaValidator {

    public void validate(String[] dna) {

        if (dna == null || dna.length == 0) {
            throw new IllegalArgumentException("El ADN no puede estar vacío");
        }

        int n = dna.length;

        for (String row : dna) {
            if (row.length() != n) {
                throw new IllegalArgumentException("El ADN debe ser una matriz NxN");
            }

            if (!row.matches("^[ATCG]+$")) {
                throw new IllegalArgumentException("El ADN contiene caracteres inválidos. Solo A, T, C, G son permitidos");
            }
        }
    }
}
