package org.utn.mutantesapi.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class DnaRecordTest {

    private final String hash = "example_hash";

    @Test
    void testNoArgsConstructor() {
        // Cubre @NoArgsConstructor
        DnaRecord record = new DnaRecord();
        assertNotNull(record);
    }

    // Eliminamos el testCustomConstructor que causaba el error de compilación.

    @Test
    void testAllArgsConstructorAndGettersSetters() {
        // Cubre @AllArgsConstructor y todos los Getters/Setters de @Data

        Long id = 1L;
        Boolean isMutant = Boolean.FALSE;
        LocalDateTime now = LocalDateTime.now();

        // Ejecuta @AllArgsConstructor
        DnaRecord record = new DnaRecord(id, this.hash, isMutant, now);

        // Cubre Getters
        assertEquals(id, record.getId());
        assertEquals(this.hash, record.getDnaHash());
        assertEquals(isMutant, record.getIsMutant());
        assertEquals(now, record.getCreatedAt());

        // Cubre Setters (fundamental para el 100% de la cobertura de la Entidad)
        record.setId(2L);
        record.setIsMutant(Boolean.TRUE);
        record.setDnaHash("new_hash");
        record.setCreatedAt(now.minusHours(1));

        assertEquals(2L, record.getId());
        assertTrue(record.getIsMutant());
        assertEquals("new_hash", record.getDnaHash());
        assertNotEquals(now, record.getCreatedAt());
    }
}