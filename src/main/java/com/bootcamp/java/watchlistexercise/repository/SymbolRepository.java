package com.bootcamp.java.watchlistexercise.repository;

import com.bootcamp.java.watchlistexercise.model.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SymbolRepository extends JpaRepository<Symbol, Long> {
    @Query("SELECT s FROM Symbol s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(s.ticker) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Symbol> findByNameOrTickerContainingKeyword(@Param("keyword") String keyword);
}
