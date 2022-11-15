package io.exiled.salesbook.controller;

import io.exiled.salesbook.service.ExportService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @PostMapping("/update-path")
    public ResponseEntity<Void> updateExportPath(@RequestBody String newPath) {
        expo.setDestination(newPath);
        return ResponseEntity.noContent().build();
    }
}
