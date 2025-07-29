package com.mx.microsicmas.model.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class StaffFindingRequest {
    private long idFinding;
    private long idStaff;
}
