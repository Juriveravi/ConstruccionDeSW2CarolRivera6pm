package com.bank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.POST, "/api/clients").hasAnyRole("CUSTOMER", "ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/clients/**").hasAnyRole("CUSTOMER", "ANALYST", "AUDITOR", "ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/api/clients/*/status").hasAnyRole("ANALYST", "ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/accounts").hasAnyRole("CUSTOMER", "ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/accounts/**").hasAnyRole("CUSTOMER", "ANALYST", "AUDITOR", "ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/accounts/*/deposit").hasAnyRole("CUSTOMER", "ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/accounts/*/withdraw").hasAnyRole("CUSTOMER", "ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/transfers").hasAnyRole("CUSTOMER", "ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/transfers/**").hasAnyRole("CUSTOMER", "SUPERVISOR", "ANALYST", "AUDITOR", "ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/api/transfers/*/approve", "/api/transfers/*/reject").hasAnyRole("SUPERVISOR", "ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/api/transfers/*/execute").hasAnyRole("SUPERVISOR", "ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/loans").hasAnyRole("CUSTOMER", "ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/loans/**").hasAnyRole("CUSTOMER", "ANALYST", "AUDITOR", "ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/api/loans/*/approve", "/api/loans/*/reject", "/api/loans/*/disburse").hasAnyRole("ANALYST", "ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/operations/**").hasAnyRole("AUDITOR", "ADMIN")
                    .anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        return new InMemoryUserDetailsManager(
                User.withUsername("customer")
                        .password(passwordEncoder.encode("customer123"))
                        .roles("CUSTOMER")
                        .build(),
                User.withUsername("analyst")
                        .password(passwordEncoder.encode("analyst123"))
                        .roles("ANALYST")
                        .build(),
                User.withUsername("supervisor")
                        .password(passwordEncoder.encode("supervisor123"))
                        .roles("SUPERVISOR")
                        .build(),
                User.withUsername("auditor")
                        .password(passwordEncoder.encode("auditor123"))
                        .roles("AUDITOR")
                        .build(),
                User.withUsername("admin")
                        .password(passwordEncoder.encode("admin123"))
                        .roles("ADMIN")
                        .build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
