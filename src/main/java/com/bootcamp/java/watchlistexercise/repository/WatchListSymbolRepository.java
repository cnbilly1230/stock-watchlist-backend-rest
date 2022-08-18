package com.bootcamp.java.watchlistexercise.repository;

import com.bootcamp.java.watchlistexercise.model.Symbol;
import com.bootcamp.java.watchlistexercise.model.WatchListSymbol;
import com.bootcamp.java.watchlistexercise.model.WatchListSymbolId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WatchListSymbolRepository extends JpaRepository<WatchListSymbol, WatchListSymbolId> {
    @Query("SELECT DISTINCT wls.symbol FROM WatchListSymbol wls")
    List<Symbol> findAllSymbols();

    @Query("SELECT wls.symbol FROM WatchListSymbol wls " +
            "WHERE wls.watchList.id = :watchListId " +
            "AND wls.watchList.uid = :uid")
    List<Symbol> findSymbolsByWatchListIdAndUid(@Param("watchListId") Long watchListId,
                                                @Param("uid") String uid);

    @Query("SELECT wls FROM WatchListSymbol wls WHERE wls.watchList.id = :watchListId " +
            "AND wls.symbol.id = :symbolId")
    Optional<WatchListSymbol> findByWatchListIdAndSymbolId(@Param("watchListId") Long watchListId,
                                                           @Param("symbolId") Long symbolId);
}
