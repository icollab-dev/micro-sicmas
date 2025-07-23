package com.mx.microsicmas.controller;

import com.mx.microsicmas.model.request.PlanningRecommendationRequest;
import com.mx.microsicmas.model.response.PlanningRecommendationResponse;
import com.mx.microsicmas.service.PlanningRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/microsicmas/recommendation")
@Validated
public class RecommendationController {
    @Autowired
    private PlanningRecommendationService planningRecommendationService;

    @PostMapping("/add")
    public ResponseEntity<List<PlanningRecommendationResponse>> createRecommendations(
            @RequestBody List<PlanningRecommendationRequest> requests
    ) {
        List<PlanningRecommendationResponse> responses = planningRecommendationService.saveRecommendations(requests);
        return ResponseEntity.ok(responses);
    }}
