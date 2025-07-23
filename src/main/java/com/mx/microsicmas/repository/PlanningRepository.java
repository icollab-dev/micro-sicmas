package com.mx.microsicmas.repository;

import com.mx.microsicmas.domain.Planning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlanningRepository extends JpaRepository<Planning, Long> {
    @Query("SELECT p FROM Planning p " +
            "LEFT JOIN FETCH p.rule " +
            "LEFT JOIN FETCH p.classification " +
            "LEFT JOIN FETCH p.auditoryEntity " +
            "LEFT JOIN FETCH p.auditoryStatus " +
            "LEFT JOIN FETCH p.approvalStatus " +
            "WHERE p.active = true")
    List<Planning> findAllActiveWithDetails();

    @Query("SELECT p FROM Planning p " +
            "LEFT JOIN FETCH p.rule " +
            "LEFT JOIN FETCH p.classification " +
            "LEFT JOIN FETCH p.auditoryEntity " +
            "LEFT JOIN FETCH p.auditoryStatus " +
            "LEFT JOIN FETCH p.approvalStatus " +
            "WHERE p.id = :id")
    Optional<Planning> findByIdWithDetails(@Param("id") Long id);
}
