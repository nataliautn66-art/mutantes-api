package org.utn.mutantesapi.service;

import org.springframework.stereotype.Component;
import org.utn.mutantesapi.exception.InvalidDnaException; // ¡Importante! Importar tu excepción

@Component
public class DnaValidator {

    public void validate(String[] dna) {

        if (dna == null || dna.length == 0) {
            // CAMBIAR: Usar InvalidDnaException
            throw new InvalidDnaException("El ADN no puede estar vacío");
        }

        int n = dna.length;

        for (String row : dna) {
            if (row.length() != n) {
                // CAMBIAR: Usar InvalidDnaException
                throw new InvalidDnaException("El ADN debe ser una matriz NxN");
            }

            if (!row.matches("^[ATCG]+$")) {
                // CAMBIAR: Usar InvalidDnaException
                throw new InvalidDnaException("El ADN contiene caracteres inválidos. Solo A, T, C, G son permitidos");
            }
        }
    }
}