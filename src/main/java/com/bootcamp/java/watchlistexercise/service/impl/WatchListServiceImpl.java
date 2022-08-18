package com.bootcamp.java.watchlistexercise.service.impl;

import com.bootcamp.java.watchlistexercise.dto.WatchListDTO;
import com.bootcamp.java.watchlistexercise.exception.ResourceNotFoundException;
import com.bootcamp.java.watchlistexercise.model.Symbol;
import com.bootcamp.java.watchlistexercise.model.WatchList;
import com.bootcamp.java.watchlistexercise.repository.WatchListRepository;
import com.bootcamp.java.watchlistexercise.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WatchListServiceImpl implements WatchListService {
    private final WatchListRepository watchListRepository;

    @Autowired
    public WatchListServiceImpl(WatchListRepository watchListRepository) {
        this.watchListRepository = watchListRepository;
    }

    @Override
    public List<WatchList> findByUID(String uid) {
        return watchListRepository.findByUid(uid);
    }

    @Override
    public WatchList findById(Long id) throws ResourceNotFoundException {
        return watchListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Watchlist not found"));
    }

    @Override
    public WatchList save(WatchList watchList) {
        return watchListRepository.save(watchList);
    }

    @Override
    public WatchList findByIdAndUid(Long id, String uid) throws ResourceNotFoundException {
        WatchList watchList = findById(id);
        return watchList;
    }

    @Override
    public WatchList updateNameByIdAndUid(WatchListDTO dto,
                                          Long id,
                                          String uid) throws ResourceNotFoundException {
        WatchList watchList = findById(id);
        watchList.setName(dto.getName());
        return watchListRepository.save(watchList);
    }

    @Override
    public void deleteByIdAndUid(Long id, String uid) throws ResourceNotFoundException {
        watchListRepository.delete(findById(id));
    }
}
