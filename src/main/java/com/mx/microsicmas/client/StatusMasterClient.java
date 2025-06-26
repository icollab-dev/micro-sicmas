package com.mx.microsicmas.client;

import com.dgc.dto.estatusmaestro.EntidadEstatusDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("estatusmaestro")
public interface StatusMasterClient {

    @GetMapping("/status/{entidad}/{estatus}")
    EntidadEstatusDTO getEntidadEstatus(@PathVariable("entidad") String entidad, @PathVariable("estatus") String estatus);

    @GetMapping("/status/{idEntidadEstatus}")
    EntidadEstatusDTO getEntidadEstatus(@PathVariable("idEntidadEstatus") Long idEntidadEstatus);

    @PostMapping("/status/{entidad}/{estatus}")
    EntidadEstatusDTO guarda(@PathVariable("entidad") String entidad, @PathVariable("estatus") String estatus);
}
