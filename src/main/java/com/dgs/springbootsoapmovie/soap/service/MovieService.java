package com.dgs.springbootsoapmovie.soap.service;

import java.util.List;

import com.dgs.springbootsoapmovie.soap.bean.Movie;
import com.dgs.springbootsoapmovie.soap.service.MovieDetailsService.Status;

public interface MovieService {

	public Movie getMovieById(int id);
	public List<Movie> getAllMovies();
	public Status deleteMovieById(int id);
	public Status addMovie(Movie movie);
}
