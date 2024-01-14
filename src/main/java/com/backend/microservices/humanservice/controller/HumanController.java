package com.backend.microservices.humanservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/human")
public class HumanController {

    @GetMapping("/test")
    public String test() {
        return "Hello";
    }
}
