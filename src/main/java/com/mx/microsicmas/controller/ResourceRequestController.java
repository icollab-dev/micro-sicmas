package com.mx.microsicmas.controller;

import com.mx.microsicmas.model.request.ResourceRequestRequest;
import com.mx.microsicmas.model.response.ResourceRequestResponse;
import com.mx.microsicmas.service.ResourceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/microsicmas/resource/request")
@Validated
public class ResourceRequestController {
    @Autowired
    private ResourceRequestService resourceRequestService;

    @PostMapping("/new")
    public ResponseEntity<ResourceRequestResponse> save(@Validated @RequestBody ResourceRequestRequest request) {
        return ResponseEntity.ok(resourceRequestService.save(request));
    }
}
