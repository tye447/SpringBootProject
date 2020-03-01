package com.example.springbootproject.controller;

import com.example.springbootproject.common.*;
import com.example.springbootproject.model.*;
import com.example.springbootproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
@CrossOrigin(allowCredentials = "true", maxAge = 3600)
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ResultGenerator resultGenerator;

    @GetMapping(value = "/list")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public RestResult productList() {
        List<Product> productList;
        try{
            productList=productService.listProduct();
            return resultGenerator.getSuccessResult(productList);
        }catch (Exception e){
            return resultGenerator.getFailResult(e.getMessage());
        }
    }

    @PostMapping(value = "/add")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public RestResult addProduct(@RequestParam("stock") Integer stock, @RequestParam("name") String name, @RequestParam("price") Double price) {
        try{
            Product product = (Product) EntityFactory.getEntity("Product");
            product.setName(name);
            product.setStock(stock);
            product.setPrice(price);
            productService.addProduct(product);
            return resultGenerator.getSuccessResult(ResultCode.SUCCESS.toString());
        }catch (Exception e){
            return resultGenerator.getFailResult(e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping(value = "/update")
    @ResponseStatus(HttpStatus.OK)
    public RestResult updateProduct(@RequestParam("id") Integer id, @RequestParam(value = "stock", required = false) Integer stock, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "price", required = false) Double price) {
        try{
            Product product = (Product)EntityFactory.getEntity("Product");
            product.setId(id);
            product.setName(name);
            product.setStock(stock);
            product.setPrice(price);
            productService.updateProduct(id, product);
            return resultGenerator.getSuccessResult(ResultCode.SUCCESS.toString());
        }catch (Exception e){
            return resultGenerator.getFailResult(e.getMessage());
        }
    }

    @ResponseBody
    @DeleteMapping(value = "/delete")
    @ResponseStatus(HttpStatus.OK)
    public RestResult deleteProduct(@RequestParam("id") Integer id)
    {
        try{
            productService.deleteProduct(id);
            return resultGenerator.getSuccessResult(ResultCode.SUCCESS.toString());
        }catch (Exception e){
            return resultGenerator.getFailResult(e.getMessage());
        }
    }
}
