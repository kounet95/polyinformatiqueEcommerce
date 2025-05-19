package org.example.polyinformatiquecoreapi.config;

import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AxonConfig {

    @Bean
    @Primary
    public XStreamSerializer xStreamSerializer() {
        XStreamSerializer serializer = XStreamSerializer.builder()
                .build();

        // Allow deserialization of all command and event classes in the package
        serializer.getXStream().allowTypesByWildcard(new String[] {
                "org.example.polyinformatiquecoreapi.commands.**",
                "org.example.polyinformatiquecoreapi.event.**",
                "org.example.polyinformatiquecoreapi.dto.**"
        });

        return serializer;
    }
}
