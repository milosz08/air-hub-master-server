/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "api")
public class ApiProperties {
    private String prefix;
    private String appName;
    private String mailResponder;
    private Integer otaExpiredRegisterHours;
    private Integer otaExpiredPasswordMinutes;
    private String replyResponder;
}
