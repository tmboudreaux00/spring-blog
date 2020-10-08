package com.codeup.demo.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Random;


@Controller
public class HomeController {

    Random random = new Random();
    @GetMapping("/")
    @ResponseBody
    public String hello() {
        return "This is the landing page!";
    }





    @GetMapping("/roll-dice")
    public String showForm() {
        return "dice";
    }

    @PostMapping("/roll-dice")
    public String rollDice(){
        int number = 0;
        int randNum = (random.nextInt(6) + 1);
        String message =  "You select " + number + " and your random number is :" + randNum;

        if(number == randNum) {
            message += "You won!";
        } else {
            message += "Try again!";
        }
        return "dice";
    }

}
