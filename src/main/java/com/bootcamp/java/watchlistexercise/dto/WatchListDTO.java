package com.bootcamp.java.watchlistexercise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WatchListDTO {
    private Long id;

    @NotBlank
    private String name;
}
