package com.mx.microsicmas.repository;

import com.mx.microsicmas.domain.FindingStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FindingStaffRepository extends JpaRepository<FindingStaff,Long> {
    List<FindingStaff> findByFindingIdAndActiveTrue(Long findingId);
}
