package org.utn.mutantesapi.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// ASUME QUE TU CLASE SE LLAMA StatsResponse
public class StatsResponseTest {

    @Test
    void testConstructorsAndAccessors() {
        // Valores de prueba
        long mutantCount = 40L;
        long humanCount = 100L;
        double ratio = 0.4;

        // 1. Constructor vacío y Setters (cubre @NoArgsConstructor y Setters)
        StatsResponse statsSetters = new StatsResponse();
        statsSetters.setCountMutantDna(mutantCount);
        statsSetters.setCountHumanDna(humanCount);
        statsSetters.setRatio(ratio);

        // 2. Verificar Getters
        assertEquals(mutantCount, statsSetters.getCountMutantDna());
        assertEquals(humanCount, statsSetters.getCountHumanDna());
        assertEquals(ratio, statsSetters.getRatio());

        // 3. Constructor con argumentos (cubre @AllArgsConstructor)
        StatsResponse statsArgs = new StatsResponse(mutantCount, humanCount, ratio);
        assertEquals(mutantCount, statsArgs.getCountMutantDna());
    }
}