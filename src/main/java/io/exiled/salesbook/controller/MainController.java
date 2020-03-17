package io.exiled.salesbook.controller;

import io.exiled.salesbook.model.Sale;
import io.exiled.salesbook.repos.SaleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {
    private SaleRepo saleRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/m")
    public String getSales(@RequestParam(required = false, defaultValue = "")String ename, Model model) {
        List<Sale> sales = saleRepo.findAll();
        List<Sale> saleList = saleRepo.findByName(ename);
        model.addAttribute("saleList", saleList);
        model.addAttribute("sales", sales);
        return "main_old";
    }

    @PostMapping("/main")
    public String addSale(
            @RequestParam String name,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "cat", required = false) String cat,
            @RequestParam int totalCost,
            @RequestParam(name = "description", defaultValue = "Processing...") String description,
            Model model
    ) {
        Sale sale = new Sale(totalCost, name, cat, email, description);
        saleRepo.save(sale);
        List<Sale> sales = saleRepo.findAll();
        model.addAttribute("sales", sales);

        return "main_old";
    }

    @PostMapping("search")
    public String search() {
        return "main_old";
    }

    @Autowired
    public void setSaleRepo(SaleRepo saleRepo) {
        this.saleRepo = saleRepo;
    }
}
