package com.example.pocwebflux.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class PocDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty
    @Id
    private Integer id;

    @NotNull
    @NotEmpty(message = "the name of this poc cannot be empty")
    @JsonProperty
    private String name;
}
