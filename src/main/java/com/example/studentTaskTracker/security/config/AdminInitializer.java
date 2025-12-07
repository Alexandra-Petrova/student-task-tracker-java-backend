package com.example.studentTaskTracker.security.config;

import com.example.studentTaskTracker.entity.Role;
import com.example.studentTaskTracker.entity.User;
import com.example.studentTaskTracker.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(UserRepository userRepository,
                            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!userRepository.existsByEmail("admin@mail.ru")) {
            User admin = new User();
            admin.setEmail("admin@mail.ru");
            admin.setFirstName("Admin");
            admin.setLastName("Admin");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setRole(Role.ROLE_ADMIN);

            userRepository.save(admin);

            System.out.println("✔ Admin user created: admin@mail.ru / password");
        }
    }
}

