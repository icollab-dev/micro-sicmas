package com.mx.microsicmas.service;

import com.mx.microsicmas.model.response.AuditoryEntityResponse;
import com.mx.microsicmas.model.response.CatalogResponse;
import com.mx.microsicmas.model.response.RuleResponse;
import com.mx.microsicmas.model.response.StatusResponse;

import java.util.List;

public interface CatalogService {
    List<CatalogResponse> getCatalogClasification(String type);
    List<RuleResponse> getCatalogRule();
    List<AuditoryEntityResponse> getAuditoryEntity();
    List<StatusResponse> getStatus(String type);
    List<CatalogResponse> getCatalogPriority();
}
