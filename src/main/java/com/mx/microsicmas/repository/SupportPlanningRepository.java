package com.mx.microsicmas.repository;

import com.mx.microsicmas.domain.SupportPlanning;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportPlanningRepository extends JpaRepository<SupportPlanning, Long> {
    SupportPlanning findSupportPlanningsById(long id);
    List<SupportPlanning> findByPlanningId(Long planningId);
}
