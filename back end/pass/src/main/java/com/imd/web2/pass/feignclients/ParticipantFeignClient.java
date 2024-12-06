package com.imd.web2.pass.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.imd.web2.pass.model.Participant;

@FeignClient(name = "participant",path = "api/participant")
public interface ParticipantFeignClient {
    @GetMapping(value = "/{id}")
    ResponseEntity<Participant> getParticipantById(@PathVariable("id") Integer id);
}
