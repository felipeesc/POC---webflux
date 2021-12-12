package com.example.pocwebflux.controller;

import com.example.pocwebflux.domain.PocDTO;
import com.example.pocwebflux.domain.entity.Poc;
import com.example.pocwebflux.service.PocService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class PocController {

    private final PocService pocService;

    @GetMapping
    public Flux<Poc> listAll() {
        return pocService.listAll();
    }

    @GetMapping(path = "{id}")
    public Mono<Poc> findById(@PathVariable int id) {
        return pocService.findById(id);
    }

    @PostMapping
    public Mono<Poc> save(@Valid @RequestBody PocDTO pocDTO) {
        return pocService.save(pocDTO);
    }

    @PutMapping(path = "{id}")
    public Mono<Void> update(@PathVariable int id, @Valid @RequestBody PocDTO pocDTO) {
        return pocService.update(pocDTO.withId(id));
    }

    @DeleteMapping(path = "{id}")
    public Mono<Void> delete(@PathVariable int id) {
        return pocService.delete(id);
    }
}
