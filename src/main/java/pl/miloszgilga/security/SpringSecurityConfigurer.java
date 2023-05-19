/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: SpringSecurityConfigurer.java
 * Last modified: 17/05/2023, 20:11
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

package pl.miloszgilga.security;

import lombok.RequiredArgsConstructor;

import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.jmpsl.security.SecurityUtil;
import org.jmpsl.security.filter.MiddlewareExceptionFilter;
import org.jmpsl.security.resolver.AuthResolverForRest;
import org.jmpsl.security.resolver.AccessDeniedResolverForRest;

import pl.miloszgilga.config.ApiProperties;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Configuration
@RequiredArgsConstructor
public class SpringSecurityConfigurer {

    private final ApiProperties props;
    private final Environment environment;
    private final MessageSource messageSource;

    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final MiddlewareExceptionFilter middlewareExceptionFilter;

    private final AuthResolverForRest authResolverForRest;
    private final AuthUserDetailsService authUserDetailsService;
    private final AccessDeniedResolverForRest accessDeniedResolverForRest;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        SecurityUtil.enableH2ConsoleForDev(httpSecurity, environment);
        httpSecurity
            .sessionManagement(options -> options.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(middlewareExceptionFilter, LogoutFilter.class)
            .formLogin().disable()
            .httpBasic().disable()
            .csrf().disable()
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(authResolverForRest)
                .accessDeniedHandler(accessDeniedResolverForRest)
            )
            .securityMatcher(props.getPrefix() + "/**")
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/error").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .requestMatchers(props.getPrefix() + "/auth/login").permitAll()
                .requestMatchers(props.getPrefix() + "/auth/register").permitAll()
                .requestMatchers(props.getPrefix() + "/auth/refresh").permitAll()
                .requestMatchers(props.getPrefix() + "/auth/activate").permitAll()
                .requestMatchers(props.getPrefix() + "/renew-password/request").permitAll()
                .requestMatchers(props.getPrefix() + "/renew-password/change").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Bean
    public AuthenticationManager authenticationManager() {
        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setMessageSource(messageSource);
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(authUserDetailsService);
        return new ProviderManager(provider);
    }
}
