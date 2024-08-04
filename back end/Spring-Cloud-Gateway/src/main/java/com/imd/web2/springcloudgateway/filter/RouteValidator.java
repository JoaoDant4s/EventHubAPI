package com.imd.web2.springcloudgateway.filter;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {
    public static  final List<String> openApiEndpoints = List.of("/auth/api/auth", "/eureka", "/auth/", "/event/api/event");

    public Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
}
