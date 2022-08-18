package com.bootcamp.java.watchlistexercise.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "symbols")
public class Symbol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String ticker;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private BigDecimal basePrice;
}
