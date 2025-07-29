package com.mx.microsicmas.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffFindingResponse {
    private long id;
    private String name;
    private String company;
}
