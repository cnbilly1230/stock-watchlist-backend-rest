package com.bootcamp.java.watchlistexercise.controller;

import com.bootcamp.java.watchlistexercise.dto.EmptyResponseDTO;
import com.bootcamp.java.watchlistexercise.dto.SymbolDTO;
import com.bootcamp.java.watchlistexercise.dto.WatchListSymbolRequestDTO;
import com.bootcamp.java.watchlistexercise.exception.ResourceNotFoundException;
import com.bootcamp.java.watchlistexercise.service.WatchListSymbolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/watchlist-symbols")
public class WatchListSymbolController {

    @Autowired
    private WatchListSymbolService watchListSymbolService;

    @GetMapping
    public List<SymbolDTO> getSymbolsByWatchListId(@RequestParam(name = "w") Long watchListId,
                                                   Principal principal) {
        return watchListSymbolService.findSymbolsByWatchListIdAndUid(watchListId, principal.getName())
                .stream()
                .map(SymbolDTO::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public EmptyResponseDTO addSymbolToWatchList(@Valid @RequestBody WatchListSymbolRequestDTO dto,
                                                 Principal principal)
            throws ResourceNotFoundException {
        watchListSymbolService.saveByUid(dto, principal.getName());
        return new EmptyResponseDTO();
    }

    @DeleteMapping
    public EmptyResponseDTO removeSymbolFromWatchList(@Valid @RequestBody WatchListSymbolRequestDTO dto,
                                                      Principal principal)
            throws ResourceNotFoundException {
        watchListSymbolService.removeByUid(dto, principal.getName());
        return new EmptyResponseDTO();
    }

}
