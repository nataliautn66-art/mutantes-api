package org.utn.mutantesapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.utn.mutantesapi.entity.DnaRecord;

import java.util.Optional;

/**
 * Repositorio JPA para DnaRecord.
 * Spring Data JPA genera automáticamente las implementaciones.
 */
@Repository
public interface DnaRecordRepository extends JpaRepository<DnaRecord, Long> {

    /**
     * Busca un registro por su hash SHA-256.
     * Usado para verificar si un DNA ya fue analizado (caché).
     *
     * @param dnaHash Hash SHA-256 del DNA
     * @return Optional con el registro si existe
     */
    Optional<DnaRecord> findByDnaHash(String dnaHash);

    /**
     * Cuenta cuántos registros hay con isMutant = true o false.
     * Usado para las estadísticas.
     *
     * @param isMutant true para contar mutantes, false para humanos
     * @return Cantidad de registros
     */
    long countByIsMutant(Boolean isMutant);
}