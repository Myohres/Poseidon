package com.nnk.poseidon;

import com.nnk.poseidon.domain.UserEntity;
import com.nnk.poseidon.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class PoseidonApplication implements CommandLineRunner {

   /* @Autowired
    UserRepository userRepository;*/

   /* @Autowired
    PasswordEncoder passwordEncoder;*/

    public static void main(String[] args) {
        SpringApplication.run(PoseidonApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
