package com.mx.microsicmas.model.response;

import com.mx.microsicmas.model.request.FindingRequest;
import lombok.*;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ResourceRequestResponse {
    private long id;
    private String request;
    private String status;
    private String description;
    private double cost;
    private List<FindingRequest> findings;
}
