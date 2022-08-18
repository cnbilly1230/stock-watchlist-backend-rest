package com.bootcamp.java.watchlistexercise.repository;

import com.bootcamp.java.watchlistexercise.model.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchListRepository extends JpaRepository<WatchList, Long> {
    @Query("SELECT wl FROM WatchList wl WHERE wl.uid = :uid")
    List<WatchList> findByUid(@Param("uid") String uid);
}
