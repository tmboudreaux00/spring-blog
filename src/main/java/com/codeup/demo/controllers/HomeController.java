package com.codeup.demo.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Random;


@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage(){
        return "home";
    }

}
