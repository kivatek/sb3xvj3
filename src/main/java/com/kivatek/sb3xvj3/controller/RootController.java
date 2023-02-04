package com.kivatek.sb3xvj3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * RootController
 */
@Controller
@RequiredArgsConstructor
public class RootController {

    @GetMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "index";
    }
}