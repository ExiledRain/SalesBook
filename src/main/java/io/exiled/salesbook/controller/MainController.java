package io.exiled.salesbook.controller;

import io.exiled.salesbook.model.Sale;
import io.exiled.salesbook.repos.SaleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {
    private SaleRepo saleRepo;

    @GetMapping("/")
    public String getStarted() {
        return "main";
    }

    @GetMapping("/sales")
    public String getSales(Model model) {
        List<Sale> sales = saleRepo.findAll();
        model.addAttribute("sales", sales);
        return "sales";
    }

    @PostMapping("/sales")
    public String addSale(
            @RequestParam String name,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "cat", required = false) String cat,
            @RequestParam int totalCost,
            @RequestParam(name = "description", required = true, defaultValue = "In progress...") String description,
            @RequestParam boolean isDone,
            Model model
    ) {
        Sale sale = new Sale(totalCost, name, cat, email, description, isDone);
//        String
        saleRepo.save(sale);
        List<Sale> sales = saleRepo.findAll();
        model.addAttribute("sales", sales);

        return "sales";
    }

    @PostMapping("search")
    public String search(
            @RequestParam String ename,
            Model model
    ) {
        List<Sale> saleList = saleRepo.findByName(ename);
        model.addAttribute("saleList", saleList);
        return "search";
    }

    @Autowired
    public void setSaleRepo(SaleRepo saleRepo) {
        this.saleRepo = saleRepo;
    }
}
