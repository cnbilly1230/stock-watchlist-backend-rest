package com.bootcamp.java.watchlistexercise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WatchListSymbolRequestDTO {
    @NotNull
    private Long watchListId;
    @NotNull
    private Long symbolId;
}
