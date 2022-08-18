package com.bootcamp.java.watchlistexercise.service.impl;

import com.bootcamp.java.watchlistexercise.dto.WatchListSymbolRequestDTO;
import com.bootcamp.java.watchlistexercise.exception.ResourceNotFoundException;
import com.bootcamp.java.watchlistexercise.model.Symbol;
import com.bootcamp.java.watchlistexercise.model.WatchList;
import com.bootcamp.java.watchlistexercise.model.WatchListSymbol;
import com.bootcamp.java.watchlistexercise.model.WatchListSymbolId;
import com.bootcamp.java.watchlistexercise.repository.WatchListSymbolRepository;
import com.bootcamp.java.watchlistexercise.service.SymbolService;
import com.bootcamp.java.watchlistexercise.service.WatchListService;
import com.bootcamp.java.watchlistexercise.service.WatchListSymbolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WatchListSymbolServiceImpl implements WatchListSymbolService {
    @Autowired
    private WatchListService watchListService;

    @Autowired
    private SymbolService symbolService;

    @Autowired
    private WatchListSymbolRepository watchListSymbolRepository;

    @Override
    public List<Symbol> findAllSymbols(){
        return watchListSymbolRepository.findAllSymbols();
    }

    @Override
    public List<Symbol> findSymbolsByWatchListIdAndUid(Long watchListId, String uid) {
        return watchListSymbolRepository.findSymbolsByWatchListIdAndUid(watchListId, uid);
    }

    @Override
    public WatchListSymbol findByWatchListIdAndSymbolId(Long watchListId,
                                                        Long symbolId) throws ResourceNotFoundException {
        return watchListSymbolRepository
                .findByWatchListIdAndSymbolId(watchListId, symbolId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(
                                "WatchList symbol not found by watch list id [%s], symbol id [%s]",
                                watchListId, symbolId
                        )
                ));
    }

    @Override
    public WatchListSymbol saveByUid(WatchListSymbolRequestDTO dto,
                                     String uid) throws ResourceNotFoundException {
        Symbol symbol = symbolService.findById(dto.getSymbolId());
        WatchList watchList = watchListService.findById(dto.getWatchListId());

        return watchListSymbolRepository.save(WatchListSymbol.builder()
                .id(new WatchListSymbolId())
                .symbol(symbol)
                .watchList(watchList)
                .build());
    }

    @Override
    public void removeByUid(WatchListSymbolRequestDTO dto,
                            String uid) throws ResourceNotFoundException {
        WatchListSymbol watchListSymbol = findByWatchListIdAndSymbolId(
                dto.getWatchListId(), dto.getSymbolId()
        );

        watchListSymbolRepository.delete(watchListSymbol);
    }
}
