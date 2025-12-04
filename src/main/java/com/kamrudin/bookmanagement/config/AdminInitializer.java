package com.kamrudin.bookmanagement.config;

import com.kamrudin.bookmanagement.entity.UserAccount;
import com.kamrudin.bookmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${ADMIN_EMAIL:admin@books.com}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD:Admin@123}")
    private String adminPassword;

    @Value("${ADMIN_NAME:System Admin}")
    private String adminName;

    public AdminInitializer(UserRepository userRepository,
                            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!userRepository.existsByEmail(adminEmail.toLowerCase())) {
            UserAccount admin = new UserAccount(
                    adminName,
                    adminEmail.toLowerCase(),
                    passwordEncoder.encode(adminPassword),
                    "ROLE_ADMIN"
            );
            userRepository.save(admin);
            System.out.println("Default ADMIN user created: " + adminEmail);
        }
    }
}
