package com.example.pocwebflux.util;

import com.example.pocwebflux.domain.entity.Poc;

public class PocCreator {

    public static Poc createPocToBeSaved(){
        return Poc.builder()
                .name("data")
                .build();
    }

    public static Poc createValidPoc(){
        return Poc.builder()
                .id(1)
                .name("data")
                .build();
    }

    public static Poc createvalidUpdatePoc(){
        return Poc.builder()
                .id(1)
                .name("data 2")
                .build();
    }
}
