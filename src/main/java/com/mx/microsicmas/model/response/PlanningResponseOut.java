package com.mx.microsicmas.model.response;

import lombok.Data;

@Data
public class PlanningResponseOut {
    private Long id;
    private String version;
    private String objective;
    private String scope;
    private String auditorName;
    private String summary;
    private String number;

    private String startDate;
    private String endDate;
    private String startDateAuditory;
    private String endDateAuditory;

    // IDs de relaciones
    private Long ruleId;
    private Long classificationId;
    private Long auditoryEntityId;
    private Long auditoryStatusId;
    private Long approvalStatusId;

    // Nombres de relaciones
    private String ruleName;
    private String classificationName;
    private String auditoryEntityName;
    private String auditoryStatusName;
    private String approvalStatusName;
}
