package com.mx.microsicmas.service;

import com.mx.microsicmas.model.request.PlanningRequest;
import com.mx.microsicmas.model.request.PlanningRequestUpdate;
import com.mx.microsicmas.model.response.PlanningResponse;
import com.mx.microsicmas.model.response.PlanningResponseOut;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface PlanningService {
    PlanningResponse guardarPlanning(PlanningRequest planningRequest);
    List<PlanningResponseOut> listarPlanningsActivos();
    PlanningResponseOut getPlanningById(@RequestParam("id") Long id);
    PlanningResponse actualizaPlanning(PlanningRequestUpdate planningRequestUpdate);
    Boolean eliminaPlanning(@RequestParam("id") Long id);
}
