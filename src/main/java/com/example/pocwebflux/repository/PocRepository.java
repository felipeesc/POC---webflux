package com.example.pocwebflux.repository;

import com.example.pocwebflux.domain.Poc;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PocRepository extends ReactiveCrudRepository<Poc, Integer> {
}
