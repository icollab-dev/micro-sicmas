package com.mx.microsicmas.controller;

import com.mx.microsicmas.model.request.FindingRequest;
import com.mx.microsicmas.model.response.FindingResponse;
import com.mx.microsicmas.model.response.FindingResponseOut;
import com.mx.microsicmas.service.FindingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/microsicmas/finding")
@Validated
public class FindingController {
    @Autowired
    private FindingService findingService;

    @PostMapping("/new")
    public ResponseEntity<FindingResponse> newFinding(@RequestBody @Validated FindingRequest finding) {
        return ResponseEntity.ok(findingService.save(finding));
    }
    @PostMapping("/list")
    public ResponseEntity<List<FindingResponseOut>> listFinding() {
        return ResponseEntity.ok(findingService.list());
    }
    @GetMapping("/list/{id}")
    public ResponseEntity<FindingResponseOut> listFinding(@PathVariable Long id) {
        return ResponseEntity.ok(findingService.findById(id));
    }

}
