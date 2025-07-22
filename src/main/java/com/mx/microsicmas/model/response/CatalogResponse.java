package com.mx.microsicmas.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CatalogResponse {
    private long id;
    private String name;
    private boolean active;
    private String description;
    private boolean find;
    private boolean auditory;
}
