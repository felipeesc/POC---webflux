package com.example.pocwebflux.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Table("poc")
public class Poc {
    @Id
    private Integer id;
    @NotNull
    @NotEmpty(message = "The name of this poc cannot be empty")
    private String name;
}
