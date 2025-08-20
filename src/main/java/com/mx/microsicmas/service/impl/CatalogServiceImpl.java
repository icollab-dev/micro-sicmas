package com.mx.microsicmas.service.impl;

import com.mx.microsicmas.domain.*;
import com.mx.microsicmas.model.response.AuditoryEntityResponse;
import com.mx.microsicmas.model.response.CatalogResponse;
import com.mx.microsicmas.model.response.RuleResponse;
import com.mx.microsicmas.model.response.StatusResponse;
import com.mx.microsicmas.repository.*;
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
    @Autowired
    private PriorityRepository priorityRepository;
    @Override
    public List<CatalogResponse> getCatalogClasification(String type) {
        List<ClassificationAudit> cassification = null;
        if(type.contains("find")){
            cassification = classificationAuditRepository.findClassificationAuditsByActiveTrueAndFindTrue();
        }else{
            cassification = classificationAuditRepository.findClassificationAuditsByActiveTrueAndAuditoryTrue();
        }
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
        }else if(type.equals("event")){
            listStatus = statusRepository.findStatusesByEventTrueAndActiveTrue();
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

    @Override
    public List<CatalogResponse> getCatalogPriority() {
        List<Priority> prioritiesDb = priorityRepository.findPriorityByActiveTrue();
        List<CatalogResponse> catalogs = new ArrayList<CatalogResponse>();
      for (Priority p : prioritiesDb) {
          CatalogResponse cat = new CatalogResponse();
          cat.setId(p.getId());
          cat.setName(p.getName());
          catalogs.add(cat);
      }
        return catalogs;
    }
}
