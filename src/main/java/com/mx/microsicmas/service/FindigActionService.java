package com.mx.microsicmas.service;

import com.mx.microsicmas.model.request.FindingActionRequest;
import com.mx.microsicmas.model.request.FindingActionUpdateRequest;
import com.mx.microsicmas.model.response.FindingActionResoponse;

import java.util.List;

public interface FindigActionService {
    FindingActionResoponse save(FindingActionRequest request);
    List<FindingActionResoponse> listByFinding(Long id);
    FindingActionResoponse update(FindingActionUpdateRequest request);
    Boolean delete(Long id);
}
