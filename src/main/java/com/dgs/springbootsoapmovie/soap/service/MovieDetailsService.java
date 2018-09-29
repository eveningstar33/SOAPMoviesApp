package com.dgs.springbootsoapmovie.soap.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dgs.springbootsoapmovie.soap.bean.Movie;
import com.dgs.springbootsoapmovie.soap.repository.MovieRepository;

@Service
public class MovieDetailsService implements MovieService {
	
	public enum Status {
		
		SUCCESS, FAILURE;
	}

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

	@Override
	public Status deleteMovieById(int id) {

		List<Movie> movies = new ArrayList<>();
		
		movieRepository.findAll().forEach(c -> movies.add(c)); 
		
		Iterator<Movie> iterator = movies.iterator(); 
		while (iterator.hasNext()) {
			Movie movie = iterator.next();
			if (movie.getId() == id) {
				movieRepository.deleteById(id); 
				return Status.SUCCESS;
			}
		}
		return Status.FAILURE;
	}

	@Override
	public Status addMovie(Movie movie) {
		
		List<Movie> movies = (List<Movie>) movieRepository.findMovieByName(movie.getName()); 
				
		if (movies.size() > 0) {
			return Status.FAILURE;
		} else {
			movieRepository.save(movie); 
		}
		
		return Status.SUCCESS; 
	}

	@Override
	public void updateMovie(Movie movie) {

		movieRepository.save(movie); 
	}
}
