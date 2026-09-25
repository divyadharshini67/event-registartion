package com.example.eventregistration.service;

import com.example.eventregistration.entity.Admin;
import com.example.eventregistration.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin login(String username, String password) {

        Admin admin = adminRepository.findByUsername(username).orElse(null);

        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }

        return null;
    }
}