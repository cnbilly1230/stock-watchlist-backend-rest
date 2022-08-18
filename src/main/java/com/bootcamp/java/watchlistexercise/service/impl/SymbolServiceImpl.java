package com.bootcamp.java.watchlistexercise.service.impl;

import com.bootcamp.java.watchlistexercise.dto.SymbolDTO;
import com.bootcamp.java.watchlistexercise.exception.ResourceNotFoundException;
import com.bootcamp.java.watchlistexercise.model.Symbol;
import com.bootcamp.java.watchlistexercise.repository.SymbolRepository;
import com.bootcamp.java.watchlistexercise.service.SymbolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SymbolServiceImpl implements SymbolService {
    private final SymbolRepository symbolRepository;

    @Autowired
    public SymbolServiceImpl(SymbolRepository symbolRepository) {
        this.symbolRepository = symbolRepository;
    }

    @Override
    public Symbol findById(Long id) throws ResourceNotFoundException {
        return symbolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Symbol not found"));
    }

    @Override
    public List<Symbol> findAll() {
        return symbolRepository.findAll();
    }

    @Override
    public List<Symbol> findByNameOrTickerContainingKeyword(String query) {
        return symbolRepository.findByNameOrTickerContainingKeyword(query);
    }

    @Override
    public Symbol updateById(SymbolDTO dto, Long id) throws ResourceNotFoundException {
        Symbol entity = findById(id);
        entity.setName(dto.getName());
        entity.setTicker(dto.getTicker());
        return symbolRepository.save(entity);
    }
}
