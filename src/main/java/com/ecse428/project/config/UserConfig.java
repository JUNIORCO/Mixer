package com.ecse428.project.config;

import java.util.List;

import com.ecse428.project.model.User;
import com.ecse428.project.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner userCommandLineRunner(UserRepository userRepository) {
        return args -> {
            User john = new User("JohnDoe9@gmail.com", "12345678");

            userRepository.saveAll(List.of(john));
        };
    }
}