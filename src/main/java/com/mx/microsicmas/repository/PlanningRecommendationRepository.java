package com.mx.microsicmas.repository;

import com.mx.microsicmas.domain.Planning;
import com.mx.microsicmas.domain.PlanningRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PlanningRecommendationRepository extends JpaRepository<PlanningRecommendation, Long> {
    List<PlanningRecommendation> findAllByPlanningInAndActiveTrue(Collection<Planning> plannings);
}
