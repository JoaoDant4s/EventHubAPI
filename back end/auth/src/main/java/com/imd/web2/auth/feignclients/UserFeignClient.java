package com.imd.web2.auth.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.imd.web2.auth.model.User;

@FeignClient(name = "user", path = "/user")
public interface UserFeignClient {
    @GetMapping(value = "/{email}")
    ResponseEntity<User> findByEmail(@PathVariable("email") String email);
}
