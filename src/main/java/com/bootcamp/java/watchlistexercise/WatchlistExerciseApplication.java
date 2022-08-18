package com.bootcamp.java.watchlistexercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WatchlistExerciseApplication {

	public static void main(String[] args) {
		SpringApplication.run(WatchlistExerciseApplication.class, args);
	}

}
