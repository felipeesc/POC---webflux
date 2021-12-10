package com.example.pocwebflux.service;

import com.example.pocwebflux.domain.Poc;
import com.example.pocwebflux.repository.PocRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class PocService {
    private final PocRepository pocRepository;

    public Flux<Poc> listAll(){
        return pocRepository.findAll();
    }
}
