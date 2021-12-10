package com.example.pocwebflux.controller;

import com.example.pocwebflux.domain.Poc;
import com.example.pocwebflux.service.PocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
public class PocController {

    @Autowired
    private PocService pocService;

    @GetMapping
    public Flux<Poc> listAll() {
        return pocService.listAll();
    }

    @GetMapping(path = "{id}")
    public Mono<Poc> findById(@PathVariable int id) {
        return pocService.findById(id);
    }
}
