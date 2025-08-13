package com.mx.microsicmas.service.impl;

import com.mx.microsicmas.domain.Finding;
import com.mx.microsicmas.domain.FindingAction;
import com.mx.microsicmas.domain.Rule;
import com.mx.microsicmas.model.request.FindingActionRequest;
import com.mx.microsicmas.model.request.FindingRequest;
import com.mx.microsicmas.model.response.FindingActionResoponse;
import com.mx.microsicmas.repository.FindingActionRepository;
import com.mx.microsicmas.repository.FindingRepository;
import com.mx.microsicmas.service.FindigActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindigActionServiceImpl implements FindigActionService {
    @Autowired
    private FindingActionRepository findingActionRepository;
    @Autowired
    private FindingRepository findingRepository;

    @Override
    public FindingActionResoponse save(FindingActionRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        FindingAction findingAction = new FindingAction();

        Finding finding = findingRepository.findById(request.getFindingId())
                .orElseThrow(() -> new RuntimeException("Finding no encontrada"));
        findingAction.setFinding(finding);
        findingAction.setName(request.getName());
        findingAction.setStatusId(request.getStatus());
        findingAction.setTargetDate(java.sql.Date.valueOf(LocalDate.parse(request.getTargetDate(), formatter)));
        findingAction.setResponsable(request.getResponsable());
        findingAction.setObservation(request.getObservation());
        findingAction.setEndDate(java.sql.Date.valueOf(LocalDate.parse(request.getEndDate(), formatter)));
        findingAction.setActive(true);
        FindingAction findingActionsaved = findingActionRepository.save(findingAction);
        return FindingActionResoponse.builder()
                .id(findingActionsaved.getId())
                .name(findingActionsaved.getName())
                .status(findingActionsaved.getStatusId())
                .targetDate(findingActionsaved.getTargetDate().toString())
                .endDate(findingActionsaved.getEndDate().toString())
                .responsable(findingActionsaved.getResponsable())
                .observation(findingActionsaved.getObservation())
                .build();

    }
    public List<FindingActionResoponse> listByFinding(Long id) {
        List<FindingAction> resposnes = findingActionRepository.findByFindingIdAndActiveTrue(id);
        return resposnes.stream().map(
                rs->{
                    FindingActionResoponse findingActionResoponse = new FindingActionResoponse();
                    findingActionResoponse.setId(rs.getId());
                    findingActionResoponse.setName(rs.getName());
                    findingActionResoponse.setStatus(rs.getStatusId());
                    findingActionResoponse.setTargetDate(rs.getTargetDate().toString());
                    findingActionResoponse.setEndDate(rs.getEndDate().toString());
                    findingActionResoponse.setResponsable(rs.getResponsable());
                    findingActionResoponse.setObservation(rs.getObservation());
                    return findingActionResoponse;
                }
        ).collect(Collectors.toList());
    }
}
