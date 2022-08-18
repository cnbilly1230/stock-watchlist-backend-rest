package com.bootcamp.java.watchlistexercise.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "watchlists_symbols")
public class WatchListSymbol {
    @Id
    private WatchListSymbolId id;

    @ManyToOne
    @JoinColumn(name = "watchlist_id", nullable = false)
    @MapsId("watchListId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private WatchList watchList;

    @ManyToOne
    @JoinColumn(name = "symbol_id", nullable = false)
    @MapsId("symbolId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Symbol symbol;
}
