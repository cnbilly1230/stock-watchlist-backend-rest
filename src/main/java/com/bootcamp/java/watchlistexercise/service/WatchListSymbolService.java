package com.bootcamp.java.watchlistexercise.service;

import com.bootcamp.java.watchlistexercise.dto.WatchListSymbolRequestDTO;
import com.bootcamp.java.watchlistexercise.exception.ResourceNotFoundException;
import com.bootcamp.java.watchlistexercise.model.Symbol;
import com.bootcamp.java.watchlistexercise.model.WatchListSymbol;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface WatchListSymbolService {
    List<Symbol> findAllSymbols();

    List<Symbol> findSymbolsByWatchListIdAndUid(Long watchListId, String uid);

    WatchListSymbol findByWatchListIdAndSymbolId(Long watchListId, Long symbolId) throws ResourceNotFoundException;

    WatchListSymbol saveByUid(WatchListSymbolRequestDTO dto, String uid) throws ResourceNotFoundException;

    void removeByUid(WatchListSymbolRequestDTO dto, String uid) throws ResourceNotFoundException;
}
