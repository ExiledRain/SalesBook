package io.exiled.salesbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("/sall")
public class MainController {

    @RequestMapping
    public String getIndex() {
        return "dindex";
    }
}
