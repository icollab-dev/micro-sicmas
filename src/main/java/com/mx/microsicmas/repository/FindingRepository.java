package com.mx.microsicmas.repository;

import com.mx.microsicmas.domain.Finding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface FindingRepository extends JpaRepository<Finding, Long> {
    Optional<Finding> findById(Long id);
    List<Finding> findAllByActiveTrue();
    List<Finding> findAllByPlanningIdAndActiveTrue(Long planningId);
    //List<Finding> findAllActiveWithDetails();
}
