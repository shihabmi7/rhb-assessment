package com.shihab.rhbaccess.external;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/external")
public class ExternalApiController {

    private final ExternalApiService service;

    public ExternalApiController(ExternalApiService service) {
        this.service = service;
    }

    @GetMapping("/google")
    public ExternalApiResult google() {
        return service.checkGoogle();
    }
}
