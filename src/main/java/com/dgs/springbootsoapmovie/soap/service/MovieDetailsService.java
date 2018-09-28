package com.dgs.springbootsoapmovie.soap.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dgs.springbootsoapmovie.soap.bean.Movie;
import com.dgs.springbootsoapmovie.soap.repository.MovieRepository;

@Service
public class MovieDetailsService implements MovieService {

	@Autowired
	private MovieRepository movieRepository;
	
	@Override
	public Movie getMovieById(int id) {

		Movie movie = movieRepository.findMovieById(id);
		
		return movie;
	}

	@Override
	public List<Movie> getAllMovies() {

		List<Movie> movies = new ArrayList<>();
		
		movieRepository.findAll().forEach(c -> movies.add(c)); 
		
		return movies;
	}

}
