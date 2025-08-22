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
public class FindingActionUpdateRequest {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private Long status;
    @NotBlank
    private String targetDate;
    @NotBlank
    private String endDate;
    @NotBlank
    private String responsable;
    @NotBlank
    private String observation;
    @NotNull(message = "El findingId no se ha encontrado en el body")
    private long findingId;
}
