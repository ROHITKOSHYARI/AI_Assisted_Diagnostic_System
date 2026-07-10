package com.rohit.diagnostic_system.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@Slf4j
public class HealthCheck {

    @PostMapping("/health_check")
    public ResponseEntity<?> healthCheck() {
        log.info("Health check requested");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
