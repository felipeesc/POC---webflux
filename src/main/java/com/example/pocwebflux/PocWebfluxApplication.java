package com.example.pocwebflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class PocWebfluxApplication {
    static {
        BlockHound.install();
    }
    public static void main(String[] args) {
        SpringApplication.run(PocWebfluxApplication.class, args);
    }

}
