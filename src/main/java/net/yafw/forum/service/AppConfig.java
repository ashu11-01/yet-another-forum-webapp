package net.yafw.forum.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${security.jwt.secret}")
    private String jwtSecret;
    @Value("${security.jwt.validity}")
    private long tokenValidityInMinutes;

    public String getJwtSecret() {
        return jwtSecret;
    }

    public long getTokenValidityInMinutes() {
        return tokenValidityInMinutes;
    }
}
