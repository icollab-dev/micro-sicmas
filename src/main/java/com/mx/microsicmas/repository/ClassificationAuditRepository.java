package com.mx.microsicmas.repository;

import com.mx.microsicmas.domain.ClassificationAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClassificationAuditRepository extends JpaRepository<ClassificationAudit, Long> {
    List<ClassificationAudit> findClassificationAuditsByActiveTrueAndAuditoryTrue();
    List<ClassificationAudit> findClassificationAuditsByActiveTrueAndFindTrue();
}
