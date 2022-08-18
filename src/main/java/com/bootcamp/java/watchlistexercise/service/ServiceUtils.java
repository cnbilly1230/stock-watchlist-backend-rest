package com.bootcamp.java.watchlistexercise.service;

import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class ServiceUtils {
    private static final ZoneId HONG_KONG_ZONE_ID = ZoneId.of("Asia/Hong_Kong");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String getCurrentTimestamp() {
        return DATE_TIME_FORMATTER.format(ZonedDateTime.now(HONG_KONG_ZONE_ID));
    }
}
