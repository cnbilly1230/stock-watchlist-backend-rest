package com.bootcamp.java.watchlistexercise.dto;

import com.bootcamp.java.watchlistexercise.model.Symbol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SymbolDTO {

    private Long id;

    @NotBlank
    private String ticker;

    @NotBlank
    private String name;

    private BigDecimal basePrice;

    public Symbol toTransientEntity() {
        return Symbol.builder()
                .ticker(ticker)
                .name(name)
                .basePrice(basePrice)
                .build();
    }

    public static SymbolDTO toDto(Symbol symbol) {
        return SymbolDTO.builder()
                .id(symbol.getId())
                .ticker(symbol.getTicker())
                .name(symbol.getName())
                .build();
    }
}
