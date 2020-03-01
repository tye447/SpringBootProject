package com.example.springbootproject.controller;

import com.example.springbootproject.common.*;
import com.example.springbootproject.model.*;
import com.example.springbootproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("commande")
@CrossOrigin(allowCredentials = "true", maxAge = 3600)
public class CommandeController {
    @Autowired
    private CommandeService commandeService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ResultGenerator resultGenerator;

    @GetMapping(value = "/list")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public RestResult commandeList() {
        List<Commande> commandeList;
        try {
            commandeList=commandeService.listCommande();
            return resultGenerator.getSuccessResult(ResultCode.SUCCESS.toString(),commandeList);
        }catch (Exception e){
            return resultGenerator.getFailResult(e.getMessage());
        }
    }

    @PostMapping(value = "/add")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public RestResult addCommande(@RequestParam("client_name") String client_name,
                              @RequestParam("employee_name") String employee_name,
                              @RequestParam("product_name") String product_name,
                              @RequestParam("quantity") Integer quantity) {
        try {
            Commande commande = (Commande) EntityFactory.getEntity("Commande");
            Client client = clientService.findByName(client_name);
            commande.setClient(client);
            commande.setEmployee(employeeService.findByName(employee_name).get(0));
            commande.setProduct(productService.findByName(product_name));
            commande.setQuantity(quantity);
            commande.setState("no_confirmed");
            commandeService.addCommande(commande);
            return resultGenerator.getSuccessResult(ResultCode.SUCCESS.toString(),commandeService.listCommande());
        }catch (Exception e){
            return resultGenerator.getFailResult(e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping(value = "/update")
    @ResponseStatus(HttpStatus.OK)
    public RestResult updateCommande(@RequestParam("id") Integer id,
                                   @RequestParam(value = "client_name", required = false) String client_name,
                                   @RequestParam(value = "employee_name", required = false) String employee_name,
                                   @RequestParam(value = "product_name", required = false) String product_name,
                                   @RequestParam(value = "quantity", required = false) Integer quantity) throws NullPointerException {
        try{
            Commande commande = (Commande) EntityFactory.getEntity("Commande");
            commande.setId(id);
            commande.setClient(clientService.findByName(client_name));
            commande.setEmployee(employeeService.findByName(employee_name).get(0));
            commande.setProduct(productService.findByName(product_name));
            commande.setQuantity(quantity);
            commandeService.updateCommande(id, commande);
            return resultGenerator.getSuccessResult(ResultCode.SUCCESS.toString(),commandeService.listCommande());
        }catch (Exception e){
            return resultGenerator.getFailResult(e.getMessage());
        }
    }

    @ResponseBody
    @DeleteMapping(value = "/delete")
    @ResponseStatus(HttpStatus.OK)
    public RestResult deleteCommande(@RequestParam("id") Integer id) {
        try{
            commandeService.deleteCommande(id);
            return resultGenerator.getSuccessResult(ResultCode.SUCCESS.toString(),commandeService.listCommande());
        }catch (Exception e){
            return resultGenerator.getFailResult(e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping(value = "/confirm")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public RestResult confirmCommande(@RequestParam("id") Integer id) {
        try{
            Commande commande = commandeService.findById(id);
            productService.reduceStock(commande);
            return resultGenerator.getSuccessResult(ResultCode.SUCCESS.toString(),commandeService.listCommande());
        }catch (Exception e){
            return resultGenerator.getFailResult(e.getMessage());
        }
    }
}
