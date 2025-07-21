package com.globsest.testsoap;

import com.globsest.testsoap.entity.Roles;
import com.globsest.testsoap.repository.RolesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RolesRepository roleRepository;

    public DataInitializer(RolesRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            List<String> roles = List.of("ADMIN", "OPERATOR", "USER");
            roles.forEach(name -> {
                Roles role = new Roles();
                role.setName(name);
                roleRepository.save(role);
            });
        }
    }
}
