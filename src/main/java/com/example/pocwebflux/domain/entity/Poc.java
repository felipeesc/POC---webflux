package com.example.pocwebflux.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Table("poc")
public class Poc {
    @Id
    private Integer id;

    private String name;
}
