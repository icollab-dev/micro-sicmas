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
public class FindingUpdateRequest {
    @NotNull
    private Long id;
    private String numFinding;
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
    @NotNull
    private Long statusApproval;
    @NotNull
    private Long statusEvent;
}
