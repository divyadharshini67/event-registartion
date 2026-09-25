package com.example.eventregistration;

import com.example.eventregistration.entity.Admin;
import com.example.eventregistration.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EventRegistrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventRegistrationApplication.class, args);
    }

    @Bean
    CommandLineRunner createAdmin(AdminRepository adminRepository) {
        return args -> {

            if (adminRepository.findByUsername("admin").isEmpty()) {

                Admin admin = new Admin();

                admin.setUsername("admin");
                admin.setPassword("admin123");

                adminRepository.save(admin);

                System.out.println("Default admin created");
            }
        };
    }
}