package com.sketchuling.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainController {

    @GetMapping("/")
    public String main(){
        return "main/main";
    }

    @GetMapping("/specific")
    public String specific(){
        return "main/specific";
    }
}
