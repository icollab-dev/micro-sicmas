package com.mx.microsicmas.service.impl;

import com.mx.microsicmas.domain.*;
import com.mx.microsicmas.model.request.PlanningRequest;
import com.mx.microsicmas.model.request.PlanningRequestUpdate;
import com.mx.microsicmas.model.response.FindingResponseOut;
import com.mx.microsicmas.model.response.PlanningResponse;
import com.mx.microsicmas.model.response.PlanningResponseOut;
import com.mx.microsicmas.model.response.RecommendationDTO;
import com.mx.microsicmas.repository.*;
import com.mx.microsicmas.service.PlanningService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlanningServiceImpl implements PlanningService {
    @Autowired
    private PlanningRepository planningRepository;
    @Autowired
    private RuleRepository ruleRepository;
    @Autowired
    private ClassificationAuditRepository classificationAuditRepository;
    @Autowired
    private AuditoryEntityRepository auditoryEntityRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private PlanningRecommendationRepository recommendationRepository;
    @Autowired
    private  FindingRepository findingRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Override
    public PlanningResponse guardarPlanning(PlanningRequest planningRequest) {
        Planning planning = new Planning();
        BeanUtils.copyProperties(planningRequest, planning, "startDate", "endDate", "startDateAuditory", "endDateAuditory");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (planningRequest.getStartDate() != null) {
            planning.setStartDate(java.sql.Date.valueOf(LocalDate.parse(planningRequest.getStartDate(), formatter)));
        }
        if (planningRequest.getEndDate() != null) {
            planning.setEndDate(java.sql.Date.valueOf(LocalDate.parse(planningRequest.getEndDate(), formatter)));
        }
        if (planningRequest.getStartDateAuditory() != null) {
            planning.setStartDateAuditory(java.sql.Date.valueOf(LocalDate.parse(planningRequest.getStartDateAuditory(), formatter)));
        }
        if (planningRequest.getEndDateAuditory() != null) {
            planning.setEndDateAuditory(java.sql.Date.valueOf(LocalDate.parse(planningRequest.getEndDateAuditory(), formatter)));
        }
        Rule rule = ruleRepository.findById(planningRequest.getRuleId())
                .orElseThrow(() -> new RuntimeException("Rule no encontrada"));

        planning.setRule(rule);
        ClassificationAudit classification = classificationAuditRepository.findById(planningRequest.getClasificationId())
                .orElseThrow(() -> new RuntimeException("ClassificationAudit no encontrada"));
        planning.setClassification(classification);

        AuditoryEntity auditoryEntity = auditoryEntityRepository.findById(planningRequest.getAuditoryEntityId())
                        .orElseThrow(() -> new RuntimeException("AuditoryEntity no encontrada"));
        planning.setAuditoryEntity(auditoryEntity);
        Status statusAuditory = statusRepository.findById(planningRequest.getAuditoryStatusId())
                        .orElseThrow(() -> new RuntimeException("StatusAudit no encontrada"));
        planning.setAuditoryStatus(statusAuditory);
        Status statusApproval = statusRepository.findById(planningRequest.getStatusApprovalId())
                        .orElseThrow(() -> new RuntimeException("StatusApproval no encontrada"));
        planning.setApprovalStatus(statusApproval);
        planning.setActive(true);

        planning = planningRepository.save(planning);
        PlanningResponse planningResponse= new PlanningResponse();
        planningResponse.setStartDate(planning.getStartDate() != null
                ? formatter.format(((java.sql.Date) planning.getStartDate()).toLocalDate())
                : null);
        planningResponse.setEndDate(planning.getEndDate() != null
                ? formatter.format(((java.sql.Date) planning.getEndDate()).toLocalDate())
                : null);
        planningResponse.setStartDateAuditory(planning.getStartDateAuditory() != null
                ? formatter.format(((java.sql.Date) planning.getStartDateAuditory()).toLocalDate())
                : null);
        planningResponse.setEndDateAuditory(planning.getEndDateAuditory() != null
                ? formatter.format(((java.sql.Date) planning.getEndDateAuditory()).toLocalDate())
                : null);

        BeanUtils.copyProperties(planning, planningResponse, "startDate", "endDate", "startDateAuditory", "endDateAuditory");
        return planningResponse;
    }
    public List<PlanningResponseOut> listarPlanningsActivos() {
        List<Planning> plannings = planningRepository.findAllActiveWithDetails();
        List<PlanningRecommendation> allRecs = recommendationRepository.findAllByPlanningInAndActiveTrue(plannings);
        Map<Long, List<RecommendationDTO>> recsByPlanningId = allRecs.stream()
                .collect(Collectors.groupingBy(
                        rec -> rec.getPlanning().getId(),
                        Collectors.mapping(rec -> {
                            RecommendationDTO dto = new RecommendationDTO();
                            dto.setId(rec.getId());
                            dto.setName(rec.getName());
                            dto.setRecommendation(rec.getRecommendation());
                            return dto;
                        }, Collectors.toList())
                ));

        return plannings.stream()
                .map(p -> {
                    List<RecommendationDTO> recs = recsByPlanningId.getOrDefault(p.getId(), new ArrayList<>());
                    return mapToResponse(p, recs, Arrays.asList());
                })
                .collect(Collectors.toList());


    }

    @Override
    public PlanningResponseOut getPlanningById(Long id) {
        Planning planning = planningRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new EntityNotFoundException("Planning no encontrado con id: " + id));
        List<Finding> findings = findingRepository.findAllByPlanningIdAndActiveTrue(id);
        List<FindingResponseOut> findingResponses = findings.stream()
                .map(this::mapToResponseFinding)
                .collect(Collectors.toList());
        List<RecommendationDTO> recommendationDTOs = planning.getRecommendations().stream()
                .map(rec -> {
                    RecommendationDTO dto = new RecommendationDTO();
                    dto.setId(rec.getId());
                    dto.setName(rec.getName());
                    dto.setRecommendation(rec.getRecommendation());
                    return dto;
                })
                .collect(Collectors.toList());

        return mapToResponse(planning,recommendationDTOs,findingResponses);
    }

    public PlanningResponseOut mapToResponse(Planning p,List<RecommendationDTO> listRecommen,List<FindingResponseOut> findingResponses) {
        PlanningResponseOut response = new PlanningResponseOut();

        response.setId(p.getId());
        response.setVersion(p.getVersion());
        response.setObjective(p.getObjetive());
        response.setScope(p.getScope());
        response.setAuditorName(p.getAuditorName());
        response.setSummary(p.getSummary());
        response.setNumber(p.getNumber());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        response.setStartDate(p.getStartDate() != null
                ? p.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter)
                : null);

        response.setEndDate(p.getEndDate() != null
                ? p.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter)
                : null);

        response.setStartDateAuditory(p.getStartDateAuditory() != null
                ? p.getStartDateAuditory().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter)
                : null);

        response.setEndDateAuditory(p.getEndDateAuditory() != null
                ? p.getEndDateAuditory().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter)
                : null);

        response.setRuleId(p.getRule() != null ? p.getRule().getId() : null);
        response.setRuleName(p.getRule() != null ? p.getRule().getName() : null);

        response.setClassificationId(p.getClassification() != null ? p.getClassification().getId() : null);
        response.setClassificationName(p.getClassification() != null ? p.getClassification().getName() : null);

        response.setAuditoryEntityId(p.getAuditoryEntity() != null ? p.getAuditoryEntity().getId() : null);
        response.setAuditoryEntityName(p.getAuditoryEntity() != null ? p.getAuditoryEntity().getName() : null);

        response.setAuditoryStatusId(p.getAuditoryStatus() != null ? p.getAuditoryStatus().getId() : null);
        response.setAuditoryStatusName(p.getAuditoryStatus() != null ? p.getAuditoryStatus().getName() : null);

        response.setApprovalStatusId(p.getApprovalStatus() != null ? p.getApprovalStatus().getId() : null);
        response.setApprovalStatusName(p.getApprovalStatus() != null ? p.getApprovalStatus().getName() : null);
        
        response.setFindings(findingResponses);
        response.setRecommendations(listRecommen);

        return response;
    }

    public PlanningResponse actualizaPlanning(PlanningRequestUpdate planningRequestUpdate){
        Planning planingDb= planningRepository.findById(planningRequestUpdate.getId())
                .orElseThrow(()-> new EntityNotFoundException("Planning no encontrado con id: " + planningRequestUpdate.getId()));
        //atribitos a actualziar

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (planningRequestUpdate.getStartDate() != null) {
            planingDb.setStartDate(java.sql.Date.valueOf(LocalDate.parse(planningRequestUpdate.getStartDate(), formatter)));
        }
        if (planningRequestUpdate.getEndDate() != null) {
            planingDb.setEndDate(java.sql.Date.valueOf(LocalDate.parse(planningRequestUpdate.getEndDate(), formatter)));
        }
        if (planningRequestUpdate.getStartDateAuditory() != null) {
            planingDb.setStartDateAuditory(java.sql.Date.valueOf(LocalDate.parse(planningRequestUpdate.getStartDateAuditory(), formatter)));
        }
        if (planningRequestUpdate.getEndDateAuditory() != null) {
            planingDb.setEndDateAuditory(java.sql.Date.valueOf(LocalDate.parse(planningRequestUpdate.getEndDateAuditory(), formatter)));
        }
        Rule rule = ruleRepository.findById(planningRequestUpdate.getRuleId())
                .orElseThrow(() -> new RuntimeException("Rule no encontrada"));

        planingDb.setRule(rule);
        ClassificationAudit classification = classificationAuditRepository.findById(planningRequestUpdate.getClasificationId())
                .orElseThrow(() -> new RuntimeException("ClassificationAudit no encontrada"));
        planingDb.setClassification(classification);

        AuditoryEntity auditoryEntity = auditoryEntityRepository.findById(planningRequestUpdate.getAuditoryEntityId())
                .orElseThrow(() -> new RuntimeException("AuditoryEntity no encontrada"));
        planingDb.setAuditoryEntity(auditoryEntity);
        Status statusAuditory = statusRepository.findById(planningRequestUpdate.getAuditoryStatusId())
                .orElseThrow(() -> new RuntimeException("StatusAudit no encontrada"));
        planingDb.setAuditoryStatus(statusAuditory);
        Status statusApproval = statusRepository.findById(planningRequestUpdate.getStatusApprovalId())
                .orElseThrow(() -> new RuntimeException("StatusApproval no encontrada"));
        planingDb.setApprovalStatus(statusApproval);
        if(planningRequestUpdate.getVersion()!=null){
            planingDb.setVersion(planningRequestUpdate.getVersion());
        }
        if(planningRequestUpdate.getObjetive()!=null){
            planingDb.setObjetive(planningRequestUpdate.getObjetive());
        }
        if(planningRequestUpdate.getScope()!=null){
            planingDb.setScope(planningRequestUpdate.getScope());
        }
        if(planningRequestUpdate.getAuditorName()!=null){
            planingDb.setAuditorName(planningRequestUpdate.getAuditorName());
        }
        if(planningRequestUpdate.getSummary()!=null){
            planingDb.setSummary(planningRequestUpdate.getSummary());
        }
        if(planningRequestUpdate.getNumber()!=null){
            planingDb.setNumber(planningRequestUpdate.getNumber());
        }
        // termina atributos a actualizar
        Planning planingUpdated = planningRepository.save(planingDb);
        PlanningResponse planningResponse= new PlanningResponse();
        planningResponse.setStartDate(planingUpdated.getStartDate() != null
                ? formatter.format(((java.sql.Date) planingUpdated.getStartDate()).toLocalDate())
                : null);
        planningResponse.setEndDate(planingUpdated.getEndDate() != null
                ? formatter.format(((java.sql.Date) planingUpdated.getEndDate()).toLocalDate())
                : null);
        planningResponse.setStartDateAuditory(planingUpdated.getStartDateAuditory() != null
                ? formatter.format(((java.sql.Date) planingUpdated.getStartDateAuditory()).toLocalDate())
                : null);
        planningResponse.setEndDateAuditory(planingUpdated.getEndDateAuditory() != null
                ? formatter.format(((java.sql.Date) planingUpdated.getEndDateAuditory()).toLocalDate())
                : null);
        planningResponse.setRuleId(planingUpdated.getRule().getId());
        planningResponse.setAuditoryEntityId(planingUpdated.getAuditoryEntity().getId());
        planningResponse.setAuditoryStatusId(planingUpdated.getAuditoryStatus().getId());
        planningResponse.setStatusApprovalId(planingUpdated.getApprovalStatus().getId());
        planningResponse.setClasificationId(planingUpdated.getClassification().getId());
        BeanUtils.copyProperties(planingUpdated, planningResponse, "startDate", "endDate", "startDateAuditory", "endDateAuditory");
        return planningResponse;

    }
    public Boolean eliminaPlanning(Long id) {
        Planning planning = planningRepository.findById(id).orElseThrow(() -> new RuntimeException("Planning no encontrada"));
        planning.setActive(false);
        planningRepository.save(planning);
        return true;
    }
    private FindingResponseOut mapToResponseFinding(Finding finding) {

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
