package com.mx.microsicmas.controller;

import com.mx.microsicmas.model.request.PlanningRequest;
import com.mx.microsicmas.model.request.PlanningRequestUpdate;
import com.mx.microsicmas.model.response.PlanningResponse;
import com.mx.microsicmas.model.response.PlanningResponseOut;
import com.mx.microsicmas.service.PlanningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/microsicmas/planning")
@Validated
public class PlanningController {
    @Autowired
    private PlanningService planningService;

    @PostMapping("/new")
    public ResponseEntity<PlanningResponse> guardarPlanning(@Validated @RequestBody PlanningRequest planningRequest) {
        return ResponseEntity.ok(planningService.guardarPlanning(planningRequest));
    }
    @GetMapping("/list/all")
    public ResponseEntity<List<PlanningResponseOut>> obtenerPlanningsActivos() {
        return ResponseEntity.ok(planningService.listarPlanningsActivos());
    }
    @GetMapping("/list/{id}")
    public ResponseEntity<PlanningResponseOut> getPlanning(@PathVariable Long id) {
        return ResponseEntity.ok(planningService.getPlanningById(id));
    }
    @PutMapping()
    public ResponseEntity<PlanningResponse> actualizarPlanning(@Validated @RequestBody PlanningRequestUpdate planningRequestUpdate) {
        return ResponseEntity.ok(planningService.actualizaPlanning(planningRequestUpdate));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> eliminarPlanning(@PathVariable Long id) {

        return ResponseEntity.ok(planningService.eliminaPlanning(id));
    }
}
