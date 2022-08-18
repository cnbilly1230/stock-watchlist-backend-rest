package com.bootcamp.java.watchlistexercise.service;

import com.bootcamp.java.watchlistexercise.dto.SymbolDTO;
import com.bootcamp.java.watchlistexercise.exception.ResourceNotFoundException;
import com.bootcamp.java.watchlistexercise.model.Symbol;

import java.util.List;

public interface SymbolService {
    Symbol findById(Long id) throws ResourceNotFoundException;

    List<Symbol> findAll();

    List<Symbol> findByNameOrTickerContainingKeyword(String query);

    Symbol updateById(SymbolDTO dto, Long id) throws ResourceNotFoundException;
}
