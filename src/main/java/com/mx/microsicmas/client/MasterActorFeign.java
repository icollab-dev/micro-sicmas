package com.mx.microsicmas.client;

import com.dgc.dto.masterActor.MasterActorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("masteractors")
public interface MasterActorFeign {

    @PostMapping("/masteractors/save/actor")
    public MasterActorDTO saveActor(@RequestBody MasterActorDTO masterActorDTO) throws Exception;

    @PostMapping("/masteractors/obten/traking")
    public MasterActorDTO obtenTraking(@RequestBody MasterActorDTO masterActorDTO) throws Exception;
    
    @PostMapping("/masteractors/obten/chronological/traking")
    public MasterActorDTO obtenChronologicalTraking(@RequestBody MasterActorDTO masterActorDTO) throws Exception; 
}
