package org.launchcode.theBridge2.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("theBridge")
public class HomeController {

    @GetMapping()
    public String displayHomePage(){
        return "homepage";
    }
}
