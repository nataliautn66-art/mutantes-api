package org.utn.mutantesapi.repository;

import org.springframework.stereotype.Repository;
import org.utn.mutantesapi.entity.DnaRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

@Repository
public interface DnaRecordRepository extends JpaRepository<DnaRecord, Long>{
    Optional<DnaRecord> findByDnaHash(String dnaHash);
    long countByIsMutant(Boolean isMutant);
}
