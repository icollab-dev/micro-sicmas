package com.mx.microsicmas.service.impl;

import com.mx.microsicmas.domain.Finding;
import com.mx.microsicmas.domain.FindingAction;
import com.mx.microsicmas.domain.Rule;
import com.mx.microsicmas.domain.Status;
import com.mx.microsicmas.model.request.FindingActionRequest;
import com.mx.microsicmas.model.request.FindingActionUpdateRequest;
import com.mx.microsicmas.model.request.FindingRequest;
import com.mx.microsicmas.model.response.FindingActionResoponse;
import com.mx.microsicmas.repository.FindingActionRepository;
import com.mx.microsicmas.repository.FindingRepository;
import com.mx.microsicmas.repository.StatusRepository;
import com.mx.microsicmas.service.FindigActionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
    @Autowired
    private StatusRepository statusRepository;

    @Override
    public FindingActionResoponse save(FindingActionRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        FindingAction findingAction = new FindingAction();

        Finding finding = findingRepository.findById(request.getFindingId())
                .orElseThrow(() -> new RuntimeException("Finding no encontrada"));
        Status status = statusRepository.findById(request.getStatus())
                .orElseThrow(() -> new RuntimeException("Status no encontrada"));
        findingAction.setFinding(finding);
        findingAction.setName(request.getName());
        findingAction.setStatusId(status);
        findingAction.setTargetDate(java.sql.Date.valueOf(LocalDate.parse(request.getTargetDate(), formatter)));
        findingAction.setResponsable(request.getResponsable());
        findingAction.setObservation(request.getObservation());
        findingAction.setEndDate(java.sql.Date.valueOf(LocalDate.parse(request.getEndDate(), formatter)));
        findingAction.setActive(true);
        FindingAction findingActionsaved = findingActionRepository.save(findingAction);
        return FindingActionResoponse.builder()
                .id(findingActionsaved.getId())
                .name(findingActionsaved.getName())
                .status(findingActionsaved.getStatusId().getId())
                .targetDate(findingActionsaved.getTargetDate().toString())
                .endDate(findingActionsaved.getEndDate().toString())
                .responsable(findingActionsaved.getResponsable())
                .observation(findingActionsaved.getObservation())
                .build();

    }
    public List<FindingActionResoponse> listByFinding(Long id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<FindingAction> resposnes = findingActionRepository.findByFindingIdAndActiveTrue(id);
        return resposnes.stream().map(
                rs->{
                    FindingActionResoponse findingActionResoponse = new FindingActionResoponse();
                    findingActionResoponse.setId(rs.getId());
                    findingActionResoponse.setName(rs.getName());
                    findingActionResoponse.setStatus(rs.getStatusId().getId());
                    Timestamp targetDate = (Timestamp) rs.getTargetDate();
                    findingActionResoponse.setTargetDate(targetDate != null ? formatter.format(targetDate.toLocalDateTime().toLocalDate()) : null);
                    Timestamp endDate = (Timestamp) rs.getEndDate();
                    findingActionResoponse.setEndDate(endDate != null ? formatter.format(endDate.toLocalDateTime().toLocalDate()) : null);
                    findingActionResoponse.setResponsable(rs.getResponsable());
                    findingActionResoponse.setObservation(rs.getObservation());
                    findingActionResoponse.setFindingId(rs.getFinding().getId());
                    return findingActionResoponse;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public FindingActionResoponse update(FindingActionUpdateRequest request) {
        FindingAction findingAction = findingActionRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Finding no encontrada"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (request.getName() != null) {
            findingAction.setName(request.getName());
        }
        if (request.getStatus() != null) {
            Status status = statusRepository.findById(request.getStatus())
                    .orElseThrow(() -> new RuntimeException("Status no encontrada"));
            findingAction.setStatusId(status);
        }
        if (request.getTargetDate() != null) {
            findingAction.setTargetDate(java.sql.Date.valueOf(LocalDate.parse(request.getTargetDate(), formatter)));
        }
        if (request.getEndDate() != null) {
            findingAction.setEndDate(java.sql.Date.valueOf(LocalDate.parse(request.getEndDate(), formatter)));
        }
        if (request.getResponsable() != null) {
            findingAction.setResponsable(request.getResponsable());
        }
        if (request.getObservation() != null) {
            findingAction.setObservation(request.getObservation());
        }
        FindingAction findingActionSaved = findingActionRepository.save(findingAction);
        FindingActionResoponse resoponse = new FindingActionResoponse();
        BeanUtils.copyProperties(findingActionSaved, resoponse);
        resoponse.setId(findingActionSaved.getId());
        resoponse.setFindingId(findingActionSaved.getFinding().getId());
        resoponse.setTargetDate(findingActionSaved.getTargetDate() != null ? formatter.format(((java.sql.Date) findingActionSaved.getTargetDate()).toLocalDate()) : null);
        resoponse.setEndDate(findingActionSaved.getEndDate() !=null ? formatter.format(((java.sql.Date) findingActionSaved.getEndDate()).toLocalDate()) : null);
        resoponse.setResponsable(findingActionSaved.getResponsable());
        resoponse.setObservation(findingActionSaved.getObservation());
        resoponse.setStatus(findingActionSaved.getStatusId().getId());
        return resoponse;

    }

    @Override
    public Boolean delete(Long id) {
        FindingAction findingAction = findingActionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Finding no encontrada"));
        findingAction.setActive(false);
        findingActionRepository.save(findingAction);
        return true;
    }
}
