package com.bootcamp.java.watchlistexercise.service;

import com.bootcamp.java.watchlistexercise.dto.PriceDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class PriceProviderService {
    private final Map<String, PriceDTO> pricesBySymbol = new ConcurrentHashMap<>();

    public void updatePricesInBatch(List<PriceDTO> priceDTOs) {
        priceDTOs.forEach(dto -> updatePricesBySymbol(dto.getSymbol(), dto));
    }

    public void updatePricesBySymbol(String symbol, PriceDTO dto) {
        if (Objects.isNull(dto) || Objects.isNull(dto.getSymbol())) {
            return;
        }
        pricesBySymbol.put(symbol, dto);
    }

    public PriceDTO getPriceBySymbol(String symbol) {
        if (pricesBySymbol.containsKey(symbol)) {
            return pricesBySymbol.get(symbol);
        }
        return PriceDTO.builder().symbol(symbol).build();
    }

    public List<PriceDTO> getPricesBySymbols(List<String> symbols) {
        return symbols.stream()
                .map(pricesBySymbol::get)
                .collect(Collectors.toList());
    }

    public List<PriceDTO> getAllPrices() {
        return pricesBySymbol.values().stream()
                .sorted((o1, o2) -> o1.getSymbol().compareToIgnoreCase(o2.getSymbol()))
                .collect(Collectors.toList());
    }
}
