package com.catalogo.dragon_ball.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.catalogo.dragon_ball.entity.Roles;
import com.catalogo.dragon_ball.repository.RolRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public void run(String... args) throws Exception {
        if (rolRepository.count() == 0) {
            Roles admin = new Roles();
            admin.setNombre("ADMIN");
            rolRepository.save(admin);

            Roles cliente = new Roles();
            cliente.setNombre("CLIENTE");
            rolRepository.save(cliente);
            
            System.out.println("✅ Roles iniciales creados en la base de datos.");
        }
    }
}
