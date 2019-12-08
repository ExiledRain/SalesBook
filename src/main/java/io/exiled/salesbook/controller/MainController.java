package io.exiled.salesbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @GetMapping("/welcome")
    public String getStarted() {
        return "main";
    }

    @RequestMapping("/status")
    public String getStatus() {
        return "status";
    }
}
