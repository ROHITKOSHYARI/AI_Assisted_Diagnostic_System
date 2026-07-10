package com.rohit.diagnostic_system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class DiagnosticSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiagnosticSystemApplication.class, args);
        log.info("Diagnostic system backend started");
    }
}
