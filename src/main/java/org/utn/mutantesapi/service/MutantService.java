package org.utn.mutantesapi.service;

import org.springframework.stereotype.Service;

@Service
public class MutantService {

    public boolean isMutant(String[] dna) {
        int n = dna.length;
        char[][] m = new char[n][n];

        for (int i = 0; i < n; i++) {
            m[i] = dna[i].toCharArray();
        }

        int count = 0;

        // Horizontal
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= n - 4; j++) {
                char c = m[i][j];
                if ((c == 'A' || c == 'T' || c == 'C' || c == 'G') &&
                        c == m[i][j+1] &&
                        c == m[i][j+2] &&
                        c == m[i][j+3]) {

                    count++;
                    if (count > 1) return true;
                }
            }
        }

        // Vertical
        for (int i = 0; i <= n - 4; i++) {
            for (int j = 0; j < n; j++) {
                char c = m[i][j];
                if ((c == 'A' || c == 'T' || c == 'C' || c == 'G') &&
                        c == m[i+1][j] &&
                        c == m[i+2][j] &&
                        c == m[i+3][j]) {

                    count++;
                    if (count > 1) return true;
                }
            }
        }

        return false;
    }
}
