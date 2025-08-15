package com.mx.microsicmas.model.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PlanningRequestUpdate {
    @NotNull(message = "El atributo id es obligatorio")
    private long id;//
    @NotBlank(message = "El atributo startDate es obligatorio")
    private String startDate;//
    @NotBlank(message = "El atributo endDate es obligatorio")
    private String endDate;//
    @NotNull(message = "El atributo ruleId es obligatorio ")
    private long ruleId;//
    private String version;//
    private String objetive;//
    private String scope;//
    @NotNull(message = "El atributo auditoryEntityId es obligatorio ")
    private long auditoryEntityId;//
    private String auditorName;//
    private String summary;//
    @NotNull(message = "El atributo auditoryStatusId es obligatorio ")
    private long auditoryStatusId;//
    @NotNull(message = "El atributo approvalStatusId es obligatorio ")
    private long statusApprovalId;//
    private String number;//
    @NotNull(message = "El clasificationId approvalStatusId es obligatorio ")
    private long clasificationId;//
    private String startDateAuditory;
    private String endDateAuditory;
}
