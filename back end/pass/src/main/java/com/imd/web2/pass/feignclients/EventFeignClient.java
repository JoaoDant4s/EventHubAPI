package com.imd.web2.pass.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.imd.web2.pass.model.Event;

@FeignClient(name = "event",path = "api/event")
public interface EventFeignClient {
    @GetMapping(value = "/{id}")
    ResponseEntity<Event> getEventById(@PathVariable("id") Integer id);
}
