package com.mx.microsicmas.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AuditoryEntityResponse {
    private long id;
    private String name;
    private String description;
}
