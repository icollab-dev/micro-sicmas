package com.mx.microsicmas.service;

import com.mx.microsicmas.model.request.ResourceRequestRequest;
import com.mx.microsicmas.model.response.ResourceRequestResponse;

public interface ResourceRequestService {
    ResourceRequestResponse save(ResourceRequestRequest request);
}
