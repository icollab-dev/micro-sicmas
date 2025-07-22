package com.mx.microsicmas.service.impl;

import com.mx.microsicmas.domain.AuditoryEntity;
import com.mx.microsicmas.domain.ClassificationAudit;
import com.mx.microsicmas.domain.Rule;
import com.mx.microsicmas.domain.Status;
import com.mx.microsicmas.model.response.AuditoryEntityResponse;
import com.mx.microsicmas.model.response.CatalogResponse;
import com.mx.microsicmas.model.response.RuleResponse;
import com.mx.microsicmas.model.response.StatusResponse;
import com.mx.microsicmas.repository.AuditoryEntityRepository;
import com.mx.microsicmas.repository.ClassificationAuditRepository;
import com.mx.microsicmas.repository.RuleRepository;
import com.mx.microsicmas.repository.StatusRepository;
import com.mx.microsicmas.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    private ClassificationAuditRepository classificationAuditRepository;
    @Autowired
    private RuleRepository ruleRepository;
    @Autowired
    private AuditoryEntityRepository auditoryEntityRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Override
    public List<CatalogResponse> getCatalogClasification() {
        List<ClassificationAudit> cassification = classificationAuditRepository.findClassificationAuditsByActiveTrue();
        List<CatalogResponse> catalogs = new ArrayList<CatalogResponse>();
        for (ClassificationAudit c : cassification) {
            CatalogResponse cat = new CatalogResponse();
            cat.setId(c.getId());
            cat.setName(c.getName());
            cat.setDescription(c.getDescription());
            cat.setActive(c.isActive());
            cat.setAuditory(c.getAuditory());
            cat.setFind(c.getFind());
            catalogs.add(cat);
        }
        return catalogs;
    }

    @Override
    public List<RuleResponse> getCatalogRule() {
        List<Rule> rules = ruleRepository.findRulesByActiveTrue();
        List<RuleResponse> rulesResponse = new ArrayList<>();
        for (Rule r : rules) {
            RuleResponse rr = new RuleResponse();
            rr.setId(r.getId());
            rr.setName(r.getName());
            rulesResponse.add(rr);
        }
        return rulesResponse;
    }

    @Override
    public List<AuditoryEntityResponse> getAuditoryEntity() {
        List<AuditoryEntity> listAuditory =auditoryEntityRepository.findAditoryEntitiesByActiveTrue();
        List<AuditoryEntityResponse> auditoryEntities = new ArrayList<>();
        for (AuditoryEntity a : listAuditory) {
            AuditoryEntityResponse auditoryEntityResponse = new AuditoryEntityResponse();
            auditoryEntityResponse.setId(a.getId());
            auditoryEntityResponse.setName(a.getName());
            auditoryEntityResponse.setDescription(a.getDescription());
            auditoryEntities.add(auditoryEntityResponse);
        }
        return auditoryEntities;
    }

    @Override
    public List<StatusResponse> getStatus(String type) {
        List<Status> listStatus = new ArrayList<>();
        if (type.equals("auditory")) {
            listStatus = statusRepository.findStatusesByAuditoryTrueAndActiveTrue();
        }else{
            listStatus = statusRepository.findStatusesByApprovalTrueAndActiveTrue();
        }
        List<StatusResponse> statusResponses = new ArrayList<>();
        for (Status s : listStatus) {
            StatusResponse statusResponse = new StatusResponse();
            statusResponse.setId(s.getId());
            statusResponse.setName(s.getName());
            statusResponses.add(statusResponse);
        }
        return statusResponses;
    }
}
