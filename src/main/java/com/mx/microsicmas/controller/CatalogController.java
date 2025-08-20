package com.mx.microsicmas.controller;

import com.mx.microsicmas.model.response.AuditoryEntityResponse;
import com.mx.microsicmas.model.response.CatalogResponse;
import com.mx.microsicmas.model.response.RuleResponse;
import com.mx.microsicmas.model.response.StatusResponse;
import com.mx.microsicmas.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/microsicmas/catalogs")
@Validated
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    @GetMapping("/classifications/{type}")
    public ResponseEntity<List<CatalogResponse>> listClassification(@PathVariable String type) {
        return ResponseEntity.ok(catalogService.getCatalogClasification(type));
    }
    @GetMapping("/rules")
    public ResponseEntity<List<RuleResponse>> listRule() {
        return ResponseEntity.ok(catalogService.getCatalogRule());
    }
    @GetMapping("/auditoryEntity")
    public  ResponseEntity<List<AuditoryEntityResponse>> listAuditoryEntity() {
        return ResponseEntity.ok(catalogService.getAuditoryEntity());
    }
    @GetMapping("/status/{type}")
    public ResponseEntity<List<StatusResponse>> getCatalogStatus(@PathVariable String type) {
        if (type==null || type.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(catalogService.getStatus(type));

    }
    @GetMapping("/priorities")
    public ResponseEntity<List<CatalogResponse>> getCatalogPriorities() {
        return ResponseEntity.ok(catalogService.getCatalogPriority());
    }

}
