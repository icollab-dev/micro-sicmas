package com.mx.microsicmas.model.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FindingActionResoponse {
    private Long id;
    private String name;
    private Long status;
    private String targetDate;
    private String endDate;
    private String responsable;
    private String observation;
    private long findingId;
}
