package org.utn.mutantesapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.utn.mutantesapi.entity.DnaRecord;

@Repository
public interface DnaRecordRepository extends JpaRepository<DnaRecord, Long> {

    DnaRecord findByDnaHash(String dnaHash);

    long countByIsMutant(boolean isMutant);
}