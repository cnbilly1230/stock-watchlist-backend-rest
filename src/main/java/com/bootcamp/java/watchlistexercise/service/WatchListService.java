package com.bootcamp.java.watchlistexercise.service;

import com.bootcamp.java.watchlistexercise.dto.WatchListDTO;
import com.bootcamp.java.watchlistexercise.exception.ResourceNotFoundException;
import com.bootcamp.java.watchlistexercise.model.WatchList;

import java.util.List;

public interface WatchListService {
    String SERVICE_ENTITY_NAME = "WatchList";

    WatchList findById(Long id) throws ResourceNotFoundException;

    WatchList save(WatchList watchList);

    List<WatchList> findByUID(String uid);

    WatchList findByIdAndUid(Long id, String uid) throws ResourceNotFoundException;

    WatchList updateNameByIdAndUid(WatchListDTO dto, Long id, String uid) throws ResourceNotFoundException;

    void deleteByIdAndUid(Long id, String uid) throws ResourceNotFoundException;
}
