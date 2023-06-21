package com.sp.fc.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class CustomLoginConfig extends AbstractHttpConfigurer<CustomLoginConfig, HttpSecurity> {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilterAt(new CustomLoginFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);
    }

    public static CustomLoginConfig customLoginConfig() {
        return new CustomLoginConfig();
    };

}
