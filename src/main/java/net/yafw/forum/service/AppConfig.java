package net.yafw.forum.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AppConfig {

    @Value("${security.jwt.secret}")
    private String jwtSecret;
    @Value("${security.jwt.validity}")
    private long tokenValidityInMinutes;

}
