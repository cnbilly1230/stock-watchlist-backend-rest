package com.bootcamp.java.watchlistexercise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceDTO {
    private String symbol;
    private Long symbolId;
    private String last;
    private String high;
    private String low;
    private String change;
    private String turnover;
    private String timestamp;
}
