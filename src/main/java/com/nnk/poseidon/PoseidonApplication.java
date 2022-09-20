package com.nnk.poseidon;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class PoseidonApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PoseidonApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
