package com.bootcamp.java.watchlistexercise.controller;

import com.bootcamp.java.watchlistexercise.dto.EmptyResponseDTO;
import com.bootcamp.java.watchlistexercise.dto.WatchListDTO;
import com.bootcamp.java.watchlistexercise.dto.factory.WatchListDtoFactory;
import com.bootcamp.java.watchlistexercise.exception.ResourceNotFoundException;
import com.bootcamp.java.watchlistexercise.model.WatchList;
import com.bootcamp.java.watchlistexercise.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/watchlists")
public class WatchListController {
    @Autowired
    private WatchListService watchListService;

    @Autowired
    private WatchListDtoFactory watchListDtoFactory;

    @GetMapping
    public List<WatchListDTO> getWatchListByUID(Principal principal) {
        return watchListService.findByUID(principal.getName()).stream()
                .map(watchListDtoFactory::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public WatchListDTO getWatchListById(@PathVariable(name = "id") Long id,
                                         Principal principal)
            throws ResourceNotFoundException {
        WatchList watchList = watchListService.findByIdAndUid(id, principal.getName());
        return watchListDtoFactory.toDto(watchList);
    }

    @PostMapping
    public WatchListDTO addWatchList(@Valid @RequestBody WatchListDTO dto,
                                     Principal principal) throws ResourceNotFoundException {
        WatchList watchList = watchListDtoFactory.toTransientEntity(dto, principal.getName());
        return watchListDtoFactory.toDto(watchListService.save(watchList));
    }

    @PutMapping("/{id}")
    public WatchListDTO updateWatchList(@PathVariable(name = "id") Long id,
                                        @Valid @RequestBody WatchListDTO dto,
                                        Principal principal)
            throws ResourceNotFoundException {
        return watchListDtoFactory.toDto(
                watchListService.updateNameByIdAndUid(dto, id, principal.getName()));
    }

    @DeleteMapping("/{id}")
    public EmptyResponseDTO deleteWatchList(@PathVariable(name = "id") Long id,
                                            Principal principal)
            throws ResourceNotFoundException {
        watchListService.deleteByIdAndUid(id, principal.getName());
        return new EmptyResponseDTO();
    }
}
