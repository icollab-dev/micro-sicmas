package com.mx.microsicmas.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanningResponse {
    private long id;
    private String startDate;
    private String endDate;
    private long ruleId;
    private String version;
    private String objetive;
    private String scope;
    private long auditoryEntityId;
    private String auditorName;
    private String summary;
    private long auditoryStatusId;
    private long statusApprovalId;
    private String number;
    private long clasificationId;
    private String startDateAuditory;
    private String endDateAuditory;
}
