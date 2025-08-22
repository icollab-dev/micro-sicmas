package com.mx.microsicmas.repository;

import com.mx.microsicmas.domain.FindingAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FindingActionRepository extends JpaRepository<FindingAction, Long> {
    List<FindingAction> findByFindingIdAndActiveTrue(Long id);

    FindingAction getFindingActionById(Long id);
}
