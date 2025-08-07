package com.mx.microsicmas.model.request;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ResourceRequestRequest {
    private String request;
    private String status;
    private String description;
    private double cost;
    private List<Long> findings;
}
