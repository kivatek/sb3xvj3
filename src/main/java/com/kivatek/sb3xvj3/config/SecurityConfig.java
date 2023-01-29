package com.kivatek.sb3xvj3.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

        private AuthenticationManager authenticationManager;

        @Autowired
        private UserDetailsService userDetailsService;

        /**
         * 
         * @param httpSecurity
         * @return
         * @throws Exception
         */
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
                AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity
                                .getSharedObject(AuthenticationManagerBuilder.class);
                authenticationManagerBuilder.userDetailsService(userDetailsService);
                authenticationManager = authenticationManagerBuilder.build();

                httpSecurity.formLogin()
                                .loginPage("/login")
                                .loginProcessingUrl("/authenticate")
                                .defaultSuccessUrl("/")
                                .failureUrl("/login?error")
                                .permitAll();

                httpSecurity.logout()
                                .logoutSuccessUrl("/login?logout")
                                .permitAll();

                String[] viewList = new String[] { "/", "about" };
                httpSecurity
                                .authorizeHttpRequests(
                                                authz -> authz.requestMatchers(
                                                                PathRequest.toStaticResources().atCommonLocations())
                                                                .permitAll()
                                                                .requestMatchers("/assets/**").permitAll()
                                                                .requestMatchers(viewList)
                                                                .permitAll()
                                                                .requestMatchers("/admin").hasRole("ROLE_ADMIN")
                                                                .requestMatchers("/user").hasRole("ROLE_USER")
                                                                .anyRequest().authenticated())
                                .csrf().disable()
                                .cors()
                                .and()
                                .authenticationManager(authenticationManager)
                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                return httpSecurity.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                log.info("passwordEncoder()");
                return new BCryptPasswordEncoder();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                log.info("corsConfigurationSource()");
                var configuration = new CorsConfiguration();

                configuration.setAllowedOrigins(List.of("http://127.0.0.1:8080", "http://localhost:8080"));
                configuration.setAllowedMethods(List.of("*"));
                configuration.setAllowedHeaders(List.of("*"));
                configuration.setAllowCredentials(true);

                var source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }
}
