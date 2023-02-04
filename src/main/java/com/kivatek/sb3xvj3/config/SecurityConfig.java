package com.kivatek.sb3xvj3.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableJdbcHttpSession
public class SecurityConfig {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    private AuthenticationManager authenticationManager;

    /**
     * 
     * @param httpSecurity
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        var authenticationManagerBuilder = httpSecurity
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService);
        authenticationManager = authenticationManagerBuilder.build();

        httpSecurity.formLogin()
                .loginPage("/login").permitAll()
                .loginProcessingUrl("/authenticate").permitAll()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler);

        httpSecurity.logout()
                .logoutSuccessUrl("/login?logout").permitAll()
                .deleteCookies("JSESSIONID", "XSRF-TOKEN");

        var viewList = new String[] { "/", "/about", "/user" };
        httpSecurity.authorizeHttpRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers("/assets/**").permitAll()
                .requestMatchers("/error/**").permitAll()
                .requestMatchers(viewList).hasAnyAuthority("USER", "ADMIN")
                .requestMatchers("/admin").hasAuthority("ADMIN")
                .anyRequest().authenticated();
        httpSecurity.csrf().disable().cors();
        httpSecurity.authenticationManager(authenticationManager)
                .securityContext((securityContext) -> securityContext
                        .securityContextRepository(new DelegatingSecurityContextRepository(
                                new RequestAttributeSecurityContextRepository(),
                                new HttpSessionSecurityContextRepository())))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // tag::cookie-serializer[]
    @Bean
    public CookieSerializer cookieSerializer(@Value("${secure.cookie}") boolean secure) {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("JSESSIONID"); // <1>
        serializer.setCookiePath("/"); // <2>
        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$"); // <3>
        serializer.setUseHttpOnlyCookie(true);
        serializer.setUseSecureCookie(secure);
        return serializer;
    }
    // end::cookie-serializer[]

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://127.0.0.1:8080", "http://localhost:8080")); // 設定から読みだすようにすること
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
