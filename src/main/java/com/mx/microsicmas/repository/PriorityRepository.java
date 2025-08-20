package com.mx.microsicmas.repository;

import com.mx.microsicmas.domain.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {
    List<Priority> findPriorityByActiveTrue();
}
