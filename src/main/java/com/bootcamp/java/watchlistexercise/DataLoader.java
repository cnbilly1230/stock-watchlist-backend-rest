package com.bootcamp.java.watchlistexercise;

import com.bootcamp.java.watchlistexercise.dto.SymbolDTO;
import com.bootcamp.java.watchlistexercise.dto.SymbolWrapperDTO;
import com.bootcamp.java.watchlistexercise.model.*;
import com.bootcamp.java.watchlistexercise.repository.SymbolRepository;
import com.bootcamp.java.watchlistexercise.repository.WatchListRepository;
import com.bootcamp.java.watchlistexercise.repository.WatchListSymbolRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DataLoader {
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAutoMode;

    @Value("${symbols-file-path}")
    String symbolsFilePath;

    @Autowired
    private SymbolRepository symbolRepository;

    @Autowired
    private WatchListRepository watchListRepository;

    @Autowired
    private WatchListSymbolRepository watchListSymbolRepository;

    @PostConstruct
    public void init() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        SymbolWrapperDTO wrapperDTO;
        wrapperDTO = objectMapper.readValue(Paths.get(symbolsFilePath).toFile(), SymbolWrapperDTO.class);
        for (SymbolDTO dto : wrapperDTO.getSymbols()) {
            symbolRepository.save(dto.toTransientEntity());
        }

        if (!(ddlAutoMode.equals("create")
                || ddlAutoMode.equals("create-drop"))) {
            return;
        }

        String uid1 = "Ye99dEZCESR8hg4ITNvtvhqnUp73";
        String uid2 = "f7QNbDZ7U8PQKN6lYMFQ14tCXWF3";

        WatchList user1WatchList1 = WatchList.builder()
                .uid(uid1)
                .name("User #1's WatchList #1")
                .build();
        WatchList user1WatchList2 = WatchList.builder()
                .uid(uid1)
                .name("User #1's WatchList #2")
                .build();
        WatchList user2WatchList1 = WatchList.builder()
                .uid(uid2)
                .name("User #2's WatchList #1")
                .build();
        WatchList user2WatchList2 = WatchList.builder()
                .uid(uid2)
                .name("User #2's WatchList #2")
                .build();
        watchListRepository.saveAll(List.of(user1WatchList1, user1WatchList2,
                user2WatchList1, user2WatchList2));

        List<WatchList> watchLists = List.of(user1WatchList1, user1WatchList2, user2WatchList1, user2WatchList2);
        List<Symbol> symbols = symbolRepository.findByNameOrTickerContainingKeyword("99");

        for (WatchList watchList : watchLists) {
            int startIdx = ThreadLocalRandom.current().nextInt(symbols.size());
            int endIdx = Math.min(symbols.size() - 1, startIdx + ThreadLocalRandom.current().nextInt(1, 11));
            List<Symbol> selectedSymbols = symbols.subList(startIdx, endIdx);
            for (Symbol symbol : selectedSymbols) {
                watchListSymbolRepository.save(WatchListSymbol.builder()
                        .id(new WatchListSymbolId())
                        .watchList(watchList)
                        .symbol(symbol)
                        .build());
            }
        }
    }
}
