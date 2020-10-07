package com.codeup.demo.controllers;

import java.util.Random;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class DiceController {

    Random random = new Random();
    int randNum = (random.nextInt(6) + 1);


    @GetMapping("/roll-dice")
    public String rollDice () {
        return "roll-dice";
    }

}