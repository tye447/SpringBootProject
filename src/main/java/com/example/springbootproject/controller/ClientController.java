package com.example.springbootproject.controller;

import com.example.springbootproject.common.*;
import com.example.springbootproject.model.*;
import com.example.springbootproject.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("client")
@CrossOrigin(allowCredentials = "true", maxAge = 3600)
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ResultGenerator resultGenerator;

    @GetMapping(value = "/list")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public RestResult clientList() {
        List<Client> clientList;
        try{
            clientList=clientService.listClient();
            return resultGenerator.getSuccessResult(ResultCode.SUCCESS.toString(),clientList);
        }catch (Exception e){
            return resultGenerator.getFailResult(e.getMessage());
        }
    }

    @PostMapping(value = "/add")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public RestResult addClient(@RequestParam("name") String name, @RequestParam("description") String description) {
        try{
            Client client = (Client)EntityFactory.getEntity("Client");
            client.setName(name);
            client.setDescription(description);
            clientService.addClient(client);
            return resultGenerator.getSuccessResult(ResultCode.SUCCESS.toString(),clientService.listClient());
        }catch (Exception e){
            return resultGenerator.getFailResult(e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping(value = "/update")
    @ResponseStatus(HttpStatus.OK)
    public RestResult updateClient(@RequestParam("id") Integer id, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "description", required = false) String description) throws NullPointerException {
        try{
            Client client = (Client)EntityFactory.getEntity("Client");
            client.setId(id);
            client.setName(name);
            client.setDescription(description);
            clientService.updateClient(id,client);
            return resultGenerator.getSuccessResult(ResultCode.SUCCESS.toString(),clientService.listClient());
        }catch (Exception e){
            return resultGenerator.getFailResult(e.getMessage());
        }
    }

    @ResponseBody
    @DeleteMapping(value = "/delete")
    @ResponseStatus(HttpStatus.OK)
    public RestResult deleteClient(@RequestParam("id") Integer id) {
        try{
            clientService.deleteClient(id);
            return resultGenerator.getSuccessResult(ResultCode.SUCCESS.toString(),clientService.listClient());
        }catch (Exception e){
            return resultGenerator.getFailResult(e.getMessage());
        }

    }
}
