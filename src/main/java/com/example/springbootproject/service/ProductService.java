package com.example.springbootproject.service;

import com.example.springbootproject.model.*;
import com.example.springbootproject.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.util.Util.getNullPropertyNames;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private CommandeService commandeService;

    public List<Product> listProduct() {
        return productRepository.findAll();
    }

    public Product addProduct(Product Product) {
        return productRepository.save(Product);
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    public Product updateProduct(Integer id, Product Product) {
        Product currentInstance = productRepository.findById(id).orElse(null);
        String[] nullPropertyNames = getNullPropertyNames(Product);
        BeanUtils.copyProperties(Product, currentInstance, nullPropertyNames);
        return productRepository.save(currentInstance);
    }

    public Product findById(Integer id) {
        Product product = productRepository.findById(id).orElse(null);
        return product;
    }

    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    public void reduceStock(Commande commande) {
        Product product = commande.getProduct();
        Integer quantity = commande.getQuantity();
        if (product.getStock() == 0) {
            throw new RuntimeException("商品：" + product.getName() + ",不存在.");
        }
        if (product.getStock() - quantity < 0) {
            throw new RuntimeException("商品：" + product.getName() + "库存不足.");
        }
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
        commande.setState("confirmed");
        commandeService.updateCommande(commande.getId(), commande);
    }
}
