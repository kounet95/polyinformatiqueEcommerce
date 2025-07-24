package org.example.ecpolycommand.config;

import com.thoughtworks.xstream.XStream;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AxonConfig {

    @Bean
    public XStream xStream() {
        XStream xStream = new XStream();
        // Autorise les classe
        xStream.allowTypesByWildcard(new String[] {
                "org.example.polyinformatiquecoreapi.commandEcommerce.**",
                "org.example.polyinformatiquecoreapi.eventEcommerce.**",
                "org.example.polyinformatiquecoreapi.dtoEcommerce.**",
                "org.example.ecpolyquery.query.**",
                "org.example.ecpolyquery.entity.**",
                "org.example.ecpolyquery.dto.**"
        });
        return xStream;
    }

    @Bean
    @Primary
    public XStreamSerializer defaultSerializer(XStream xStream) {
        return XStreamSerializer.builder()
                .xStream(xStream)
                .build();
    }
}

