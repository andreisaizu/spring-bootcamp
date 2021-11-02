package com.deloittedigital.library.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "integration.author.url")
public class AuthorClientConfiguration {

    private String base;
    private String specific;

    public void setBase(String base) {
        this.base = base;
    }

    public void setSpecific(String specific) {
        this.specific = specific;
    }

    public String getBase() {
        return base;
    }

    public String getSpecific() {
        return base + specific;
    }

}