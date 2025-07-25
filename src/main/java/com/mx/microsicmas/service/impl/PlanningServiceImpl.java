package com.mx.microsicmas.service.impl;

import com.mx.microsicmas.domain.*;
import com.mx.microsicmas.model.request.PlanningRequest;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
                    return mapToResponse(p, recs);
                })
                .collect(Collectors.toList());


    }

    @Override
    public PlanningResponseOut getPlanningById(Long id) {
        Planning planning = planningRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new EntityNotFoundException("Planning no encontrado con id: " + id));
        List<RecommendationDTO> recommendationDTOs = planning.getRecommendations().stream()
                .map(rec -> {
                    RecommendationDTO dto = new RecommendationDTO();
                    dto.setId(rec.getId());
                    dto.setName(rec.getName());
                    dto.setRecommendation(rec.getRecommendation());
                    return dto;
                })
                .collect(Collectors.toList());

        return mapToResponse(planning,recommendationDTOs);
    }

    public PlanningResponseOut mapToResponse(Planning p,List<RecommendationDTO> listRecommen) {
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

        response.setRecommendations(listRecommen);
        return response;
    }
}
