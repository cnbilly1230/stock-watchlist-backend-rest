package com.bootcamp.java.watchlistexercise.controller;

import com.bootcamp.java.watchlistexercise.dto.PriceDTO;
import com.bootcamp.java.watchlistexercise.dto.PriceWrapperDTO;
import com.bootcamp.java.watchlistexercise.exception.ResourceNotFoundException;
import com.bootcamp.java.watchlistexercise.model.Symbol;
import com.bootcamp.java.watchlistexercise.service.MockQuoteService;
import com.bootcamp.java.watchlistexercise.service.WatchListSymbolService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/quotes")
public class MockQuoteController {
    @Autowired
    private WatchListSymbolService watchListSymbolService;

    @Autowired
    private MockQuoteService mockQuoteService;

    @GetMapping
    public PriceWrapperDTO getQuotesByWatchlistId(@RequestParam(name = "s", required = false) Long symbolId,
                                                  @RequestParam(name = "w", required = false) Long watchlistId,
                                                  Principal principal) throws ResourceNotFoundException {
        if (!Objects.isNull(symbolId)) {
            List<PriceDTO> priceDTOs = List.of(mockQuoteService.getQuoteBySymbolId(symbolId));
            return PriceWrapperDTO.builder()
                    .data(priceDTOs)
                    .build();
        }

        if (!Objects.isNull(watchlistId)) {
            List<Symbol> symbols = watchListSymbolService.findSymbolsByWatchListIdAndUid(watchlistId, principal.getName());
            List<PriceDTO> priceDTOs = symbols.stream()
                    .map(symbol -> mockQuoteService.getQuoteBySymbol(symbol))
                    .collect(Collectors.toList());
            return PriceWrapperDTO.builder()
                    .data(priceDTOs)
                    .build();
        }

        throw new ResourceNotFoundException("Either one of symbolId or watchlistId must be provided!");
    }
}
