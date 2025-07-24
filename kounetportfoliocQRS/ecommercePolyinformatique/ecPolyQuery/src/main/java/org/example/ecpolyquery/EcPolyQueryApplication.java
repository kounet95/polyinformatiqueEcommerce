package org.example.ecpolyquery;

import org.example.ecpolyquery.entity.*;
import org.example.ecpolyquery.repos.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication

public class EcPolyQueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcPolyQueryApplication.class, args);
    }


}
