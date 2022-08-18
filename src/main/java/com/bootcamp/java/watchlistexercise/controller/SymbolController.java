package com.bootcamp.java.watchlistexercise.controller;

import com.bootcamp.java.watchlistexercise.dto.SymbolDTO;
import com.bootcamp.java.watchlistexercise.exception.ResourceNotFoundException;
import com.bootcamp.java.watchlistexercise.service.SymbolService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/symbols")
public class SymbolController {
    private final SymbolService symbolService;

    @Autowired
    public SymbolController(SymbolService symbolService) {
        this.symbolService = symbolService;
    }

    @GetMapping
    public List<SymbolDTO> getSymbolByNameOrTicker(@RequestParam(name = "q", required = false) String query) {
        if (Strings.isBlank(query)) {
            return symbolService.findAll().stream()
                    .map(SymbolDTO::toDto)
                    .collect(Collectors.toList());
        }
        return symbolService.findByNameOrTickerContainingKeyword(query).stream()
                .map(SymbolDTO::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SymbolDTO getSymbolById(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
        return SymbolDTO.toDto(symbolService.findById(id));
    }
}
