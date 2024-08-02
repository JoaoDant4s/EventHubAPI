package com.imd.web2.auth.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.imd.web2.auth.model.User;

import reactor.core.scheduler.Scheduler.Worker;


@Component
@FeignClient(name = "user",path = "/user")
public class UserFeignClient {
    @GetMapping(value = "/{email}")
    public ResponseEntity<User> findByEmail(@PathVariable String email);
}
