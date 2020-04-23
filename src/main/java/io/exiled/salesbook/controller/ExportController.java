package io.exiled.salesbook.controller;

import io.exiled.salesbook.service.ExportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping(value = "/export")
public class ExportController {
    private ExportService expo;

    public ExportController(ExportService expo) {
        this.expo = expo;
    }

    // creates pdf file
//    @RequestMapping(value = "/make",method = RequestMethod.GET)
//    @ResponseBody
//    public void makeFile(){
//        expo.createFile();
//    }

    // creates pdf table with DB dump
    @RequestMapping(value = "/table",method = RequestMethod.GET)
    @ResponseBody
    public void makeTable() throws Exception{
        expo.manipulatePdf();
    }
}
