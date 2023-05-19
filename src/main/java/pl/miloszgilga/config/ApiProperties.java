/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: ApiProperties.java
 * Last modified: 19/05/2023, 00:13
 * Project name: air-hub-master-server
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     <http://www.apache.org/license/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the license.
 */

package pl.miloszgilga.config;

import lombok.Data;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
