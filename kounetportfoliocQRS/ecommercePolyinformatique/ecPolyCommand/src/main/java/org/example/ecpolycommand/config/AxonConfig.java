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

        xStream.allowTypesByWildcard(new String[] {
                "org.example.polyinformatiquecoreapi.commandEcommerce.**",
                "org.example.polyinformatiquecoreapi.eventEcommerce.**",
                "org.example.polyinformatiquecoreapi.dtoEcommerce.**",
                "org.example.ecpolyquery.query.**",
                "org.example.ecpolyquery.entity.**",
                "org.example.ecpolycommand.saga.**",
                "org.example.ecpolyquery.dto.**",
                "package org.example.ecpolycommand.config.**",
                "org.example.ecpolycommand.service.imple.**"
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

