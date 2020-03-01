package com.example.springbootproject.model;

public class EntityFactory {
    public static Object getEntity(String entityName){
        Object object=new Object();
        switch (entityName){
            case "Client": object=new Client();break;
            case "Commande": object=new Commande();break;
            case "Employee": object=new Employee();break;
            case "Product": object=new Product();break;
            default:object=null;break;
        }
        return object;
    }
}
