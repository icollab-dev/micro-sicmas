package com.mx.microsicmas.service.impl;

import com.mx.microsicmas.domain.Planning;
import com.mx.microsicmas.domain.PlanningRecommendation;
import com.mx.microsicmas.model.request.PlanningRecommendationRequest;
import com.mx.microsicmas.model.response.PlanningRecommendationResponse;
import com.mx.microsicmas.repository.PlanningRecommendationRepository;
import com.mx.microsicmas.repository.PlanningRepository;
import com.mx.microsicmas.service.PlanningRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
@Service
public class PlanningRecommendationServiceImpl implements PlanningRecommendationService {
    @Autowired
    private PlanningRepository planningRepository;
    @Autowired
    private PlanningRecommendationRepository planningRecommendationRepository;
    @Override
    public List<PlanningRecommendationResponse> saveRecommendations(List<PlanningRecommendationRequest> requests) {
        List<PlanningRecommendationResponse> responses = new ArrayList<>();

        for (PlanningRecommendationRequest request : requests) {
            Planning planning = planningRepository.findById(request.getPlanningId())
                    .orElseThrow(() -> new EntityNotFoundException("Planning no encontrado con id: " + request.getPlanningId()));

            PlanningRecommendation entity = new PlanningRecommendation();
            entity.setPlanning(planning);
            entity.setName(getUsuarioHeaders());
            entity.setRecommendation(request.getRecommendation());
            entity.setActive(true);
            entity.setUserCreated(getUsuarioHeaders());
            entity.setDateCreated(new Date());

            PlanningRecommendation saved = planningRecommendationRepository.save(entity);

            PlanningRecommendationResponse response = new PlanningRecommendationResponse();
            response.setId(saved.getId());
            response.setPlanningId(planning.getId());
            response.setName(saved.getName());
            response.setRecommendation(saved.getRecommendation());

            responses.add(response);
        }

        return responses;
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


}
