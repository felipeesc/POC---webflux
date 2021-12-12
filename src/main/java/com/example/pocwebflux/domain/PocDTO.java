package com.example.pocwebflux.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PocDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty
    private Integer id;
    @JsonProperty
    private String name;
}
