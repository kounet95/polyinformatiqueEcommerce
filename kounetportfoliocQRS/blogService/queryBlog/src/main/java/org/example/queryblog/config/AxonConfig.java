package org.example.queryblog.config;

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
                "org.example.polyinformatiquecoreapi.commands.**",
                "org.example.polyinformatiquecoreapi.event.**",
                "org.example.polyinformatiquecoreapi.dto.**",
                "org.example.queryblog.query.**"
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

