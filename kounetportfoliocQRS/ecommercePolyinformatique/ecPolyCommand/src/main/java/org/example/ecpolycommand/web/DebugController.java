package org.example.ecpolycommand.web;

import org.example.ecpolycommand.config.StripeConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DebugController {
  @Autowired
  StripeConfigProperties config;

  @GetMapping("/test-stripe-key")
  public String getStripeKey() {
    return "API Key: " + (config.getApi().getKey() == null ? "<null>" : config.getApi().getKey() .substring(0,8) + "...");
  }
}
