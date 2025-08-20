package com.mx.microsicmas.service.impl;

import com.mx.microsicmas.domain.*;
import com.mx.microsicmas.model.request.FindingRequest;
import com.mx.microsicmas.model.request.FindingUpdateRequest;
import com.mx.microsicmas.model.response.FindingResponse;
import com.mx.microsicmas.model.response.FindingResponseOut;
import com.mx.microsicmas.model.response.RecommendationDTO;
import com.mx.microsicmas.repository.*;
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
    @Autowired
    private StatusRepository statusRepository;

    @Override
    public FindingResponse save(FindingRequest findingRequest) {
        Finding finding = new Finding();
        BeanUtils.copyProperties(findingRequest, finding);
        if(finding.getPlanning() != null) {
            Planning planning = planningRepository.findById(findingRequest.getPlanningId())
                    .orElseThrow(()->new RuntimeException("No se encotró el planning"));
            finding.setPlanning(planning);
        }else {
            finding.setPlanning(null);
        }
        Priority priority = priorityRepository.findById(findingRequest.getPriorityId())
                .orElseThrow(()->new RuntimeException("No se encontró el priority"));
        finding.setPriority(priority);
        if (findingRequest.getFindingDate() != null) {
            finding.setDate(java.sql.Date.valueOf(LocalDate.parse(findingRequest.getFindingDate(), formatter)));
        }
        if (findingRequest.getEndDate() != null) {
            finding.setEndDate(java.sql.Date.valueOf(LocalDate.parse(findingRequest.getEndDate(), formatter)));
        }
        Long numFinding = findingRepository.getLastConsecutivo();
        if(numFinding == null) {
            numFinding = 1L;
        }
        if(findingRequest.getStatusEvent() != null) {
            Status statusEvent = statusRepository.findStatusByIdAndEventTrueAndActiveTrue(findingRequest.getStatusEvent());
            if(statusEvent == null) {
                throw new EntityNotFoundException("status event not found");
            }
            finding.setStatusEvent(statusEvent);
        }
        if(findingRequest.getStatusApproval() != null) {
            Status statusApproval = statusRepository.findStatusByIdAndApprovalTrueAndActiveTrue(findingRequest.getStatusApproval());
            if(statusApproval == null) {
                throw new EntityNotFoundException("status approval not found");
            }
            finding.setStatusApproval(statusApproval);
        }
        finding.setNumFinding("HZ-".concat(String.valueOf(numFinding)));
        finding.setUserReporter(getUsuarioHeaders());
        finding.setDateReported(new Date());
        ClassificationAudit classificationAudit =classificationAuditRepository.findById(findingRequest.getClasificationId())
                .orElseThrow(()->new RuntimeException("No se encontró el classificationAudit"));
        finding.setClassification(classificationAudit);
        finding.setActive(true);
        Finding findingSaved = findingRepository.save(finding);
        FindingResponse findingResponse = new FindingResponse();
        BeanUtils.copyProperties(findingRequest, findingResponse);
        findingResponse.setId(findingSaved.getId());
        findingResponse.setNumFinding(findingSaved.getNumFinding());
        if(findingSaved.getPlanning() != null) {
            findingResponse.setPlanningId(findingSaved.getPlanning().getId());
        }
        findingResponse.setPriorityId(findingSaved.getPriority().getId());
        findingResponse.setClasificationId(findingSaved.getClassification().getId());
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
        if(finding.getPlanning() != null) {
            response.setPlanningId(finding.getPlanning().getId());
        }
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
        response.setStatusEvent(finding.getStatusEvent().getId());
        response.setStatusApproval(finding.getStatusApproval().getId());
        response.setName(finding.getName());
        response.setDescription(finding.getDescription());
        response.setPointRule(finding.getPointRule());
        return response;
    }
    @Override
    public Boolean delete(Long id) {
        Finding findingDb = findingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el hallazgo con ID: " + id));
        findingDb.setActive(false);
        findingRepository.save(findingDb);
        return true;
    }

    @Override
    public FindingResponse update(FindingUpdateRequest findingRequest) {
        Finding findingDb = findingRepository.findById(findingRequest.getId())
                .orElseThrow(()-> new EntityNotFoundException("No se encontro Hallazgo con ID: " + findingRequest.getId()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (findingRequest.getPlanningId() != null) {
            Planning planningdb =  planningRepository.findById(findingRequest.getPlanningId())
                    .orElseThrow(() -> new EntityNotFoundException("Planning no encontrado con id: "+ findingRequest.getPlanningId()    ));
            findingDb.setPlanning(planningdb);
        }
        if(findingRequest.getEndDate() != null) {
            findingDb.setEndDate(java.sql.Date.valueOf(LocalDate.parse(findingRequest.getEndDate(), formatter)));
        }
        if(findingRequest.getDescription() != null) {
            findingDb.setDescription(findingRequest.getDescription());
        }
        if(findingRequest.getName() != null) {
            findingDb.setName(findingRequest.getName());
        }
        if (findingRequest.getPointRule() != null) {
            findingDb.setPointRule(findingRequest.getPointRule());
        }
        if (findingRequest.getFindingDate() != null) {
            findingDb.setEndDate(java.sql.Date.valueOf(LocalDate.parse(findingRequest.getFindingDate(), formatter)));
        }
        if (findingRequest.getPriorityId() != null) {
             Priority prioritydb = priorityRepository.findById(findingRequest.getPriorityId())
                     .orElseThrow(()-> new EntityNotFoundException("Prioridad no encontrado con id: "+ findingRequest.getPriorityId()));
             findingDb.setPriority(prioritydb);
        }
        if(findingRequest.getClasificationId() != null) {
            ClassificationAudit classificationAudit = classificationAuditRepository.findById(findingRequest.getClasificationId())
                    .orElseThrow(()-> new EntityNotFoundException("Clasificacion no encontrado con id: "+ findingRequest.getClasificationId()));
            findingDb.setClassification(classificationAudit);
        }
        if(findingRequest.getStatusEvent() != null) {
            Status statusEvent = statusRepository.findStatusByIdAndEventTrueAndActiveTrue(findingRequest.getStatusEvent());
            if(statusEvent == null) {
                throw new EntityNotFoundException("status event not found");
            }
            findingDb.setStatusEvent(statusEvent);
        }
        if(findingRequest.getStatusApproval() != null) {
            Status statusApproval = statusRepository.findStatusByIdAndApprovalTrueAndActiveTrue(findingRequest.getStatusApproval());
            if(statusApproval == null) {
                throw new EntityNotFoundException("status approval not found");
            }
            findingDb.setStatusApproval(statusApproval);
        }
        findingRepository.save(findingDb);
        FindingResponse findingResponse = new FindingResponse();
        BeanUtils.copyProperties(findingRequest, findingResponse);
        findingResponse.setNumFinding(findingDb.getNumFinding());
        findingResponse.setStatusApproval(findingDb.getStatusApproval().getId());
        findingResponse.setStatusEvent(findingDb.getStatusEvent().getId());
        return findingResponse;
    }
}
