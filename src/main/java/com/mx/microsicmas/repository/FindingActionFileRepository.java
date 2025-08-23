package com.mx.microsicmas.repository;

import com.mx.microsicmas.domain.FindingActionFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FindingActionFileRepository extends JpaRepository<FindingActionFile, Long> {
    FindingActionFile findFindingActionFileById(Long id);
    List<FindingActionFile> findByFindingActionId(Long id);
}
