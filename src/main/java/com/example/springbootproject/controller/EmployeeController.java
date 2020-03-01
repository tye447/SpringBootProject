package com.example.springbootproject.controller;

import com.example.springbootproject.common.*;
import com.example.springbootproject.model.*;
import com.example.springbootproject.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employee")
@CrossOrigin(allowCredentials = "true", maxAge = 3600)
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ResultGenerator resultGenerator;

    @GetMapping(value = "/list")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public RestResult listEmployee() {
        List<Employee> employeeList;
        try {
            employeeList=employeeService.listEmployee();
            return resultGenerator.getSuccessResult(ResultCode.SUCCESS.toString(),employeeList);
        }catch (Exception e){
            return resultGenerator.getFailResult(e.getMessage());
        }
    }

    @PostMapping(value = "/add")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public RestResult addEmployee(@RequestParam("name") String name, @RequestParam("age") Integer age, @RequestParam("password") String password) {
        try {
            Employee employee = (Employee) EntityFactory.getEntity("Employee");
            employee.setName(name);
            employee.setPassword(password);
            employee.setAge(age);
            employeeService.addEmployee(employee);
            return resultGenerator.getSuccessResult(ResultCode.SUCCESS.toString(),employeeService.listEmployee());
        }catch (Exception e){
            return resultGenerator.getFailResult(ResultCode.FAIL.toString());
        }
    }

    @ResponseBody
    @PostMapping(value = "/update")
    @ResponseStatus(HttpStatus.OK)
    public RestResult updateEmployee(@RequestParam("id") Integer id,
                                   @RequestParam(value = "password",required = false) String password,
                                   @RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "age", required = false) Integer age) {
        try{
            Employee employee = (Employee) EntityFactory.getEntity("Employee");
            employee.setId(id);
            employee.setName(name);
            employee.setPassword(password);
            employee.setAge(age);
            employeeService.updateEmployee(id, employee);
            return resultGenerator.getSuccessResult(ResultCode.SUCCESS.toString(),employeeService.listEmployee());
        }catch (Exception e){
            return resultGenerator.getFailResult(ResultCode.FAIL.toString());
        }
    }

    @ResponseBody
    @DeleteMapping(value = "/delete")
    @ResponseStatus(HttpStatus.OK)
    public RestResult deleteEmployee(@RequestParam("id") Integer id) {
        try{
            employeeService.deleteEmployee(id);
            return resultGenerator.getSuccessResult(ResultCode.SUCCESS.toString(),employeeService.listEmployee());
        }catch (Exception e){
            return resultGenerator.getFailResult(ResultCode.FAIL.toString());
        }
    }

}
