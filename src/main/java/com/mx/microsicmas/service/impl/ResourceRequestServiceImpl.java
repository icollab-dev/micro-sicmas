package com.mx.microsicmas.service.impl;

import com.mx.microsicmas.domain.Finding;
import com.mx.microsicmas.domain.ResourceRequest;

import com.mx.microsicmas.model.request.ResourceRequestRequest;
import com.mx.microsicmas.model.response.ResourceRequestResponse;
import com.mx.microsicmas.repository.FindingRepository;
import com.mx.microsicmas.repository.ResourceRequestRepository;
import com.mx.microsicmas.service.ResourceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceRequestServiceImpl implements ResourceRequestService {
    @Autowired
    private ResourceRequestRepository resourceRequestRepository;
    @Autowired
    private FindingRepository findingRepository;
    public ResourceRequestResponse save(ResourceRequestRequest request) {
        ResourceRequest resourceRequest = new ResourceRequest();
        resourceRequest.setRequest(request.getRequest());
        resourceRequest.setCost(request.getCost());
        resourceRequest.setDescription(request.getDescription());
        resourceRequest.setActive(true);
        if(request.getFindings() == null || request.getFindings().size() == 0) {
            throw new IllegalArgumentException("No se han encontrado ningun hallazgo");
        }
        List<Finding> findings = findingRepository.findAvailableAndActiveFindingsByIdIn(request.getFindings());
        for (Finding finding : findings) {
            finding.setResourceRequest(resourceRequest);
        }
        if (findings.size() != request.getFindings().size()) {
            throw new IllegalArgumentException("Uno o más hallazgos no están o ya están asociados a otro solicitud.");
        }
        resourceRequest.setFindings(findings);
        //colocar status inicial
        //resourceRequest.setStatus(request.getStatus());
        ResourceRequest saved = resourceRequestRepository.save(resourceRequest);
        return ResourceRequestResponse.builder()
                .id(saved.getId())
                .request(request.getRequest())
                .cost(request.getCost())
                .status(saved.getStatus())
                .description(saved.getDescription())
                .build();
    }
}
