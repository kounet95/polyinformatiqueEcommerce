package org.example.ecpolycommand.config;

import com.google.api.client.util.Value;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "stripe")
@Getter @Setter
public class StripeConfigProperties {
  private Api api;
  private Webhook webhook;

  @Getter @Setter
  public static class Api {
    private String key;
    private String secretKey;
  }

  @Getter @Setter
  public static class Webhook {
    private String secret;
  }
}
