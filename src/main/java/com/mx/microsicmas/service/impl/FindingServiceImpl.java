package com.mx.microsicmas.service.impl;

import com.mx.microsicmas.domain.ClassificationAudit;
import com.mx.microsicmas.domain.Finding;
import com.mx.microsicmas.domain.Planning;
import com.mx.microsicmas.domain.Priority;
import com.mx.microsicmas.model.request.FindingRequest;
import com.mx.microsicmas.model.response.FindingResponse;
import com.mx.microsicmas.model.response.FindingResponseOut;
import com.mx.microsicmas.model.response.RecommendationDTO;
import com.mx.microsicmas.repository.ClassificationAuditRepository;
import com.mx.microsicmas.repository.FindingRepository;
import com.mx.microsicmas.repository.PlanningRepository;
import com.mx.microsicmas.repository.PriorityRepository;
import com.mx.microsicmas.service.FindingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindingServiceImpl implements FindingService {
    @Autowired
    private FindingRepository findingRepository;
    @Autowired
    private PlanningRepository planningRepository;
    @Autowired
    private PriorityRepository priorityRepository;
    @Autowired
    private ClassificationAuditRepository classificationAuditRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Override
    public FindingResponse save(FindingRequest findingRequest) {
        Finding finding = new Finding();
        BeanUtils.copyProperties(findingRequest, finding);
        Planning planning = planningRepository.findById(findingRequest.getPlanningId())
                .orElseThrow(()->new RuntimeException("No se encotró el planning"));
        finding.setPlanning(planning);
        Priority priority = priorityRepository.findById(findingRequest.getPriorityId())
                .orElseThrow(()->new RuntimeException("No se encontró el priority"));
        finding.setPriority(priority);
        if (findingRequest.getFindingDate() != null) {
            finding.setDate(java.sql.Date.valueOf(LocalDate.parse(findingRequest.getFindingDate(), formatter)));
        }
        if (findingRequest.getEndDate() != null) {
            finding.setEndDate(java.sql.Date.valueOf(LocalDate.parse(findingRequest.getEndDate(), formatter)));
        }
        finding.setUserReporter(getUsuarioHeaders());
        finding.setDateReported(new Date());
        ClassificationAudit classificationAudit =classificationAuditRepository.findById(findingRequest.getClasificationId())
                .orElseThrow(()->new RuntimeException("No se encontró el classificationAudit"));
        finding.setClassification(classificationAudit);
        finding.setActive(true);
        findingRepository.save(finding);
        FindingResponse findingResponse = new FindingResponse();
        BeanUtils.copyProperties(findingRequest, findingResponse);
        return findingResponse;

    }

    @Override
    public FindingResponseOut findById(Long id) {
        Finding finding = findingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el hallazgo con ID: " + id));

        // Mapear a DTO de salida
       return  mapToResponse(finding);

    }

    @Override
    public List<FindingResponseOut> list() {

        List<Finding> findings = findingRepository.findAllByActiveTrue();
        return findings.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());


    }

    private static String getUsuarioHeaders() {
        String user = "";
        try {
            user = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getParameter("user");
        } catch (IllegalStateException e) {
            user = "system";
        }
        return user;
    }
    private FindingResponseOut mapToResponse(Finding finding) {

        FindingResponseOut response = new FindingResponseOut();
        response.setId(finding.getId());
        response.setNumFinding(finding.getNumFinding());
        response.setPlanningId(finding.getPlanning().getId());
        response.setFindingDate(finding.getDate().toString());
        response.setEndDate(finding.getEndDate().toString());
        if(finding.getPriority() !=null){
            response.setPriorityId(finding.getPriority().getId());
            response.setPriorityName(finding.getPriority().getName());
        }
        if(finding.getClassification() !=null){
            response.setClasificationId(finding.getClassification().getId());
            response.setClassificationName(finding.getClassification().getName());
        }
        response.setStatusEvent(finding.getStatusEvent());
        response.setStatusApproval(finding.getStatusApproval());
        response.setName(finding.getName());
        response.setDescription(finding.getDescription());
        response.setPointRule(finding.getPointRule());
        return response;
    }
}
