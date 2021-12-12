package com.example.pocwebflux.service;

import com.example.pocwebflux.domain.PocDTO;
import com.example.pocwebflux.domain.entity.Poc;
import com.example.pocwebflux.repository.PocRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PocService {
    private final PocRepository pocRepository;

    public Flux<Poc> listAll(){
        return pocRepository.findAll().log();
    }

    public Mono<Poc> findById(int id) {
        return pocRepository.findById(id)
                .switchIfEmpty(monoResponseNotFoundException(id))
                .log();
    }

    public Mono<Poc> save(PocDTO pocDTO) {
        Poc poc = new Poc();
        BeanUtils.copyProperties(pocDTO, poc);
        return pocRepository.save(poc)
                .log();
    }

    public Mono<Void> update(PocDTO pocDTO){
        Poc poc = new Poc();
        BeanUtils.copyProperties(pocDTO, poc);
        return findById(poc.getId())
                .map(pocFound -> poc.withId(pocFound.getId()))
                .flatMap(pocRepository::save)
                .thenEmpty(Mono.empty());
    }

    public <T> Mono<T> monoResponseNotFoundException(int id) {
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "objeto não encontrado com id " + id));
    }


    public Mono<Void> delete(int id) {
        return findById(id)
                .flatMap(pocRepository::delete);
    }
}
