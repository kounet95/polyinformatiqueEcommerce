package org.example.ecpolycommand;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.polyinformatiquecoreapi.commandEcommerce.*;
import org.example.polyinformatiquecoreapi.dtoEcommerce.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@SpringBootApplication
public class EcPolyCommandApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcPolyCommandApplication.class, args);
    }


}
