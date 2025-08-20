package com.mx.microsicmas.model.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FindingResponseOut {
    private Long id;
    private String numFinding;
    private Long planningId;
    private String findingDate;
    private String description;
    private String name;
    private String pointRule;
    private String endDate;
    private Long priorityId;
    private Long clasificationId;
    private Long statusApproval;
    private Long statusEvent;
    private String priorityName;
    private String classificationName;

}
