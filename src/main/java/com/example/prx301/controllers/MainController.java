package com.example.prx301.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("/")
public class MainController {
    @GetMapping
    public String home(){
        return "home";
    }
}
