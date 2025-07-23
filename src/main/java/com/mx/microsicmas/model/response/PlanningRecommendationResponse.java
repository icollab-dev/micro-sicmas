package com.mx.microsicmas.model.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PlanningRecommendationResponse {
    private Long id;
    private Long planningId;
    private String name;
    private String recommendation;
}
