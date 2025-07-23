package com.mx.microsicmas.model.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PlanningRecommendationRequest {
    private Long planningId;
    private String recommendation;
}
