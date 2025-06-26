package com.mx.microsicmas.client;

import com.dgc.dto.configuration.ProcessDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("masterconfiguration")
public interface ConfiguracionPlantaFeign {

    @GetMapping("/config/key/{process}/")
    public ProcessDTO obtenConfiguracion(@PathVariable("process") String process);

    @PostMapping("/config/guarda/")
    public ProcessDTO guardaConfiguracion(@RequestBody ProcessDTO processDTO);
}
