package com.mx.microsicmas.repository;


import com.mx.microsicmas.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Resource;
import java.util.List;

@Resource
public interface StatusRepository extends JpaRepository<Status, Long> {
    List<Status> findStatusesByAuditoryTrueAndActiveTrue();
    List<Status> findStatusesByApprovalTrueAndActiveTrue();
}
