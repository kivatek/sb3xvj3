package com.kivatek.sb3xvj3.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginResponseHandlerConfig {

    /**
     * 認証が通った際に200を応答するだけのハンドラのインスタンスを返す。<br>
     * ログイン成功後の画面遷移はクライアント側に任せる。
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {

            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                    Authentication authentication) throws IOException, ServletException {
                log.debug("LOGIN SUCCEEDED");
                if (response.isCommitted()) {
                    log.info("Response has already been committed.");
                    return;
                }

                for (GrantedAuthority authority : authentication.getAuthorities()) {
                    log.debug("AUTHORITY: " + authority.getAuthority());
                }

                response.setStatus(HttpStatus.OK.value());

                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
                }
            }
        };
    }

    /**
     * 認証に失敗した際に403を応答するだけのハンドラのインスタンスを返す。<br>
     * ログイン失敗後の画面遷移はクライアント側に任せる。
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandler() {

            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                    AuthenticationException exception) throws IOException, ServletException {
                log.info("LOGIN FAILURE");
                response.setStatus(HttpStatus.FORBIDDEN.value());
            }
        };
    }

}
