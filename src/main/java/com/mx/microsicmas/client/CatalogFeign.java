package com.mx.microsicmas.client;

import com.dgc.dto.catalogoMaestro.BaseCatalogOutDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("catalog")
public interface CatalogFeign {

    @GetMapping("/catalog/get/{catalog}")
    List<BaseCatalogOutDTO> list(@PathVariable final String catalog) throws Exception ;

}