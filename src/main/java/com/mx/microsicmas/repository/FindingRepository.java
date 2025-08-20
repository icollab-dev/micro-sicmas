package com.mx.microsicmas.repository;

import com.mx.microsicmas.domain.Finding;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface FindingRepository extends JpaRepository<Finding, Long> {
    Optional<Finding> findById(Long id);
    List<Finding> findAllByActiveTrue();
    List<Finding> findAllByPlanningIdAndActiveTrue(Long planningId);
    @Query("SELECT f FROM Finding f WHERE f.id IN :ids AND f.active = true AND f.resourceRequest IS NULL")
    List<Finding> findAvailableAndActiveFindingsByIdIn(@Param("ids") List<Long> ids);
    @Query("SELECT MAX(f.id) FROM Finding f")
    Long getLastConsecutivo();
    //List<Finding> findAllActiveWithDetails();
}
