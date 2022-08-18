package com.bootcamp.java.watchlistexercise.dto.factory;

import com.bootcamp.java.watchlistexercise.dto.WatchListDTO;
import com.bootcamp.java.watchlistexercise.model.WatchList;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import java.nio.charset.StandardCharsets;

@Component
public class WatchListDtoFactory {
    public WatchListDTO toDto(WatchList watchList) {
        return WatchListDTO.builder()
                .id(watchList.getId())
                .name(watchList.getName())
                .build();
    }

    public WatchList toTransientEntity(WatchListDTO dto,
                                       String uid) {
        return WatchList.builder()
                .name(HtmlUtils.htmlEscape(dto.getName(), StandardCharsets.UTF_8.name()))
                .uid(uid)
                .build();
    }
}
