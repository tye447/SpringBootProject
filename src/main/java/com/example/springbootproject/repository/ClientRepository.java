package com.example.springbootproject.repository;

import com.example.springbootproject.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findByName(String name);
}

