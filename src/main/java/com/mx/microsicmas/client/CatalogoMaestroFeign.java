package com.mx.microsicmas.client;

import com.dgc.dto.catalogoMaestro.MaestroOpcionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Set;

@FeignClient("mastercatalog")
public interface CatalogoMaestroFeign {

    @GetMapping("/mastercatalog/mastercatalog/{catalogo}")
    public Set<MaestroOpcionDTO> getCatalogo(@PathVariable("catalogo") String catalogo);

    @GetMapping("/mastercatalog/mastercatalog/{catalogo}/{opcion}")
    public MaestroOpcionDTO getCatalogoOpcion(@PathVariable("catalogo") String catalogo, @PathVariable("opcion") String opcion);

    @GetMapping("/mastercatalog/mastercatalog/find/{maestroOpcionId}")
    public MaestroOpcionDTO getMaestroOpcion(@PathVariable("maestroOpcionId") Long maestroOpcionId);

    @PostMapping("/mastercatalog/mastercatalog/{catalogo}/{opcion}")
    public MaestroOpcionDTO guardaCatalogoOpcion(@PathVariable("catalogo") String catalogo, @PathVariable("opcion") String opcion);

    @PostMapping("/mastercatalog/list/catalog")
    public Map<String, List<MaestroOpcionDTO>> listCatalog(@RequestBody List<String> catalogs);

    @GetMapping("/mastercatalog/current/associated/{opcionPadreId}")
    public List<MaestroOpcionDTO> getByOpcionPadreId(@PathVariable("opcionPadreId") Long opcionPadreId);
}