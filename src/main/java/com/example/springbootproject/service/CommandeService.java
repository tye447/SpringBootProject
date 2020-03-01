package com.example.springbootproject.service;

import com.example.springbootproject.model.Commande;
import com.example.springbootproject.model.Product;
import com.example.springbootproject.repository.CommandeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.util.Util.getNullPropertyNames;

@Service
public class CommandeService {
    @Autowired
    private CommandeRepository commandeRepository;

    public List<Commande> listCommande() {
        return commandeRepository.findAll();
    }

    public Commande addCommande(Commande commande) {
        return commandeRepository.save(commande);
    }

    public void deleteCommande(Integer id) {
        commandeRepository.deleteById(id);
    }

    public Commande updateCommande(Integer id, Commande commande) {
        Commande currentInstance = commandeRepository.findById(id).orElse(null);
        String[] nullPropertyNames = getNullPropertyNames(commande);
        BeanUtils.copyProperties(commande, currentInstance, nullPropertyNames);
        return commandeRepository.save(currentInstance);
    }

    public Commande findById(Integer id) {
        Optional<Commande> optionalCommande = commandeRepository.findById(id);
        return optionalCommande.get();
    }

    public Commande findByProduct(Product product) {
        return commandeRepository.findByProduct(product);
    }
}
