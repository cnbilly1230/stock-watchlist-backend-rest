package com.bootcamp.java.watchlistexercise.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class WatchListSymbolId implements Serializable {
    private static final long serialVersionUID = -3287715633608041039L;

    private Long watchListId;

    private Long symbolId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WatchListSymbolId that = (WatchListSymbolId) o;
        return Objects.equals(watchListId, that.watchListId) && Objects.equals(symbolId, that.symbolId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(watchListId, symbolId);
    }
}
