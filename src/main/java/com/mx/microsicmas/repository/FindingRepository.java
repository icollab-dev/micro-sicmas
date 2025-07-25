package com.mx.microsicmas.repository;

import com.mx.microsicmas.domain.Finding;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface FindingRepository extends CrudRepository<Finding, Long> {
    Optional<Finding> findById(Long id);
    List<Finding> findAllByActiveTrue();
    //List<Finding> findAllActiveWithDetails();
}
