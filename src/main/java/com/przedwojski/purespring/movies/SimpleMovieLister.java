package com.przedwojski.purespring.movies;

import org.springframework.stereotype.Service;

@Service
public class SimpleMovieLister {

    private final MovieFinder movieFinder;

    public SimpleMovieLister(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

    @Override
    public String toString() {
        return "SimpleMovieLister{" +
            "movieFinder=" + movieFinder +
            '}';
    }
}
