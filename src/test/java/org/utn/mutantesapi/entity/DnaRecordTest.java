package org.utn.mutantesapi.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class DnaRecordTest {

    private final String hash = "example_hash";

    @Test
    void testNoArgsConstructor() {
        // 1. Cubre @NoArgsConstructor
        DnaRecord record = new DnaRecord();
        assertNotNull(record);
    }

    @Test
    void testCustomConstructor() {
        // 2. Ahora usa el @AllArgsConstructor con 4 parámetros
        DnaRecord record = new DnaRecord(null, this.hash, true, LocalDateTime.now());

        // Verifica que los campos sean correctos y que la fecha de creación se asigne
        assertEquals(this.hash, record.getDnaHash());
        assertTrue(record.getIsMutant());
        assertNotNull(record.getCreatedAt());
    }

    @Test
    void testAllArgsConstructorAndGettersSetters() {
        // 3. Cubre @AllArgsConstructor y todos los Getters/Setters generados por @Data

        // Define todos los campos requeridos por @AllArgsConstructor
        Long id = 1L;
        Boolean isMutant = false;
        LocalDateTime now = LocalDateTime.now();

        // Ejecuta @AllArgsConstructor
        DnaRecord record = new DnaRecord(id, this.hash, isMutant, now);

        // 4. Cubre Getters
        assertEquals(id, record.getId());
        assertEquals(this.hash, record.getDnaHash());
        assertEquals(isMutant, record.getIsMutant());
        assertEquals(now, record.getCreatedAt());

        // 5. Cubre Setters
        record.setId(2L);
        record.setIsMutant(true);

        assertEquals(2L, record.getId());
        assertTrue(record.getIsMutant());
    }
}