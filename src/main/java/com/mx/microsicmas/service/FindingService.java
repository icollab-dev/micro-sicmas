package com.mx.microsicmas.service;

import com.mx.microsicmas.model.request.FindingRequest;
import com.mx.microsicmas.model.request.FindingUpdateRequest;
import com.mx.microsicmas.model.response.FindingResponse;
import com.mx.microsicmas.model.response.FindingResponseOut;

import java.util.List;

public interface FindingService {
    FindingResponse save(FindingRequest findingRequest);
    FindingResponseOut findById(Long id);
    List<FindingResponseOut> list();
    Boolean delete(Long id);
    FindingResponse update(FindingUpdateRequest findingRequest);
}
