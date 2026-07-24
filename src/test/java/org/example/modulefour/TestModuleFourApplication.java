package org.example.modulefour;

import org.springframework.boot.SpringApplication;

public class TestModuleFourApplication {

    public static void main(String[] args) {
        SpringApplication.from(ModuleFourApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
