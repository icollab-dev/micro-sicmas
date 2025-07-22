package com.mx.microsicmas.repository;

import com.mx.microsicmas.domain.AuditoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditoryEntityRepository extends JpaRepository<AuditoryEntity,Long> {
    List<AuditoryEntity> findAditoryEntitiesByActiveTrue();
}
