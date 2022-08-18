package com.bootcamp.java.watchlistexercise.service;

import com.bootcamp.java.watchlistexercise.dto.PriceDTO;
import com.bootcamp.java.watchlistexercise.exception.ResourceNotFoundException;
import com.bootcamp.java.watchlistexercise.model.Symbol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@DependsOn({"dataLoader"})
public class MockQuoteService {
    private static final double PRICE_PERCENT_CHANGE_RANGE = 0.05;
    private static final double TURNOVER_VALUE_CHANGE_RANGE = 0.5;

    private final Map<Symbol, PriceDTO> quoteStore = new ConcurrentHashMap<>();

    @Autowired
    private SymbolService symbolService;

    public PriceDTO getQuoteBySymbol(Symbol symbol) {
        return quoteStore.get(symbol);
    }

    public PriceDTO getQuoteBySymbolId(Long id) throws ResourceNotFoundException {
        return quoteStore.get(symbolService.findById(id));
    }

    @PostConstruct
    private void init() {
        List<Symbol> symbols = symbolService.findAll();
        symbols.forEach(symbol -> {
            PriceDTO newQuote = PriceDTO.builder()
                    .symbol(symbol.getTicker())
                    .symbolId(symbol.getId())
                    .last(symbol.getBasePrice().toString())
                    .high(symbol.getBasePrice().toString())
                    .low(symbol.getBasePrice().toString())
                    .change("+0.0(0.00%)")
                    .turnover("0.00M")
                    .timestamp(ServiceUtils.getCurrentTimestamp())
                    .build();
            quoteStore.put(symbol, newQuote);
        });
        log.info("[init] quoteStore: {}", quoteStore);
    }

    @Scheduled(fixedDelay = 2000)
    public void updateMockQuotes() {
        log.info("[updateMockQuotes] Updating mock quotes...");
        for (Symbol symbol : quoteStore.keySet()) {
            try {
                updateMockQuote(symbol);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    private void updateMockQuote(Symbol symbol) {
        PriceDTO oldQuote = quoteStore.get(symbol);

        String newLastStr = generateNewLastPrice(oldQuote.getLast());

        PriceDTO newQuote = PriceDTO.builder()
                .symbol(symbol.getTicker())
                .symbolId(symbol.getId())
                .last(newLastStr)
                .high(findHigh(oldQuote.getHigh(), oldQuote.getLast()))
                .low(findLow(oldQuote.getLow(), oldQuote.getLast()))
                .change(findChange(symbol.getBasePrice().toString(), newLastStr))
                .turnover(generateTurnover(oldQuote.getTurnover()))
                .timestamp(ServiceUtils.getCurrentTimestamp())
                .build();

        quoteStore.put(symbol, newQuote);
    }

    private static String generateNewLastPrice(String oldValueStr) {
        BigDecimal percentChange = BigDecimal.valueOf(
                ThreadLocalRandom.current().nextDouble(-PRICE_PERCENT_CHANGE_RANGE, PRICE_PERCENT_CHANGE_RANGE));
        return new BigDecimal(oldValueStr).multiply(BigDecimal.ONE.subtract(percentChange))
                .setScale(2, RoundingMode.HALF_EVEN)
                .toString();
    }

    private static String findHigh(String oldValueStr, String newValueStr) {
        BigDecimal oldValue = new BigDecimal(oldValueStr);
        BigDecimal newValue = new BigDecimal(newValueStr);
        if (oldValue.compareTo(newValue) >= 0) {
            return oldValueStr;
        }
        return newValueStr;
    }

    private static String findLow(String oldValueStr, String newValueStr) {
        BigDecimal oldValue = new BigDecimal(oldValueStr);
        BigDecimal newValue = new BigDecimal(newValueStr);
        if (oldValue.compareTo(newValue) <= 0) {
            return oldValueStr;
        }
        return newValueStr;
    }

    private static String findChange(String basePriceStr, String newLastStr) {
        BigDecimal oldValue = new BigDecimal(basePriceStr);
        BigDecimal newValue = new BigDecimal(newLastStr);

        BigDecimal absoluteChange = newValue.subtract(oldValue).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal percentChange = (newValue.subtract(oldValue)).divide(oldValue, RoundingMode.HALF_EVEN)
                .multiply(new BigDecimal("100"))
                .setScale(2, RoundingMode.HALF_EVEN);

        String sign = absoluteChange.compareTo(BigDecimal.ZERO) >= 0 ? "+" : "-";

        return sign + absoluteChange.abs() + "(" + sign + percentChange.abs() + "%)";
    }

    private static String generateTurnover(String oldValueStr) {
        oldValueStr = oldValueStr.replace("M", "");

        BigDecimal absoluteChange = BigDecimal.valueOf(
                ThreadLocalRandom.current().nextDouble(TURNOVER_VALUE_CHANGE_RANGE));

        BigDecimal oldValue = new BigDecimal(oldValueStr);

        BigDecimal result = oldValue.add(absoluteChange).setScale(2, RoundingMode.HALF_EVEN);

        return result.toString() + "M";
    }
}