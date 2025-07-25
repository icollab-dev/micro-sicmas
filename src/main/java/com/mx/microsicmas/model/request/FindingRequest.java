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
public class FindingRequest {
    @NotBlank
    private String numFinding;
    @NotNull
    private Long planningId;
    @NotBlank
    private String findingDate;
    @NotBlank
    private String description;
    @NotBlank
    private String name;
    @NotBlank
    private String pointRule;
    @NotBlank
    private String endDate;
    @NotNull
    private Long priorityId;
    @NotNull
    private Long clasificationId;
    /*
    private String userReporter;
    private String dateReported;
    private String userSupervised;
    private String dateSupervised;
    private String userApproval;
    private String dateApproved;
    private String userReject;
    private String dateRejected;
    private String statusApproval;
    private String statusEvent;
    */
}
