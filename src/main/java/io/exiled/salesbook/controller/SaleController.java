package io.exiled.salesbook.controller;

import io.exiled.salesbook.model.Sale;
import io.exiled.salesbook.repos.SaleRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/sale")
public class SaleController {
    private SaleRepo saleRepo;

    public SaleController(SaleRepo saleRepo) {
        this.saleRepo = saleRepo;
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    @ResponseBody
    public List<Sale> getAll() {
        return saleRepo.findAll();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Sale add(@RequestBody Sale sale){
        return saleRepo.save(sale);
    }

    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Sale getById(@RequestParam("id") Long id) {
        return saleRepo.getOne(id);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Sale update(@PathVariable("id") Sale saleFromDb,@RequestBody Sale sale) {
        BeanUtils.copyProperties(sale,saleFromDb,"id");
        return saleRepo.save(saleFromDb);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") Sale sale) {
        saleRepo.delete(sale);
    }

    @RequestMapping(value = "/clear", method = RequestMethod.DELETE)
    @ResponseBody
    public void clear() {
        saleRepo.deleteAll();
    }
}















