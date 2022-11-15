package io.exiled.salesbook.controller;

import io.exiled.salesbook.service.ExportService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/export")
public class ExportController {
    private ExportService expo;

    public ExportController(ExportService expo) {
        this.expo = expo;
    }

    @RequestMapping(value = "/table", method = RequestMethod.GET)
    @ResponseBody
    public void makeTable() throws Exception {
        expo.manipulatePdf();
    }

    @PostMapping("/set")
    public void updateExportPath(@RequestBody String newPath) {
        expo.setDestination(newPath);
    }

    @GetMapping("/get")
    @ResponseBody
    public String getExportPath() {
        return expo.getDestination();
    }
}
