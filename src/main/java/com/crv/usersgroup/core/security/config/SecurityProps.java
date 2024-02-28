package com.crv.usersgroup.core.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.config")
public class SecurityProps {

    public static String PREFIX = "Bearer ";
    public static String KEY = "secret";
    public static Long EXPIRATION_MILLIS = 3600000L;

    public void setPREFIX(String prefix) {
        SecurityProps.PREFIX = prefix.trim() + " ";
    }

    public void setKEY(String key) {
        SecurityProps.KEY = key;
    }

    public void setExpirationMillis(Long expirationMillis) {
        SecurityProps.EXPIRATION_MILLIS = expirationMillis;
    }

}

