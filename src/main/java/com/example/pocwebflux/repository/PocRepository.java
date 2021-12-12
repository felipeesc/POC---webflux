package com.example.pocwebflux.repository;

import com.example.pocwebflux.domain.entity.Poc;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface PocRepository extends ReactiveCrudRepository<Poc, Integer> {
    Mono<Poc> findById(int id);
}
