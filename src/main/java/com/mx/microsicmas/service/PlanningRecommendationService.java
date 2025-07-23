package com.mx.microsicmas.service;

import com.mx.microsicmas.model.request.PlanningRecommendationRequest;
import com.mx.microsicmas.model.response.PlanningRecommendationResponse;

import java.util.List;

public interface PlanningRecommendationService {
    List<PlanningRecommendationResponse> saveRecommendations(List<PlanningRecommendationRequest> request);
}
