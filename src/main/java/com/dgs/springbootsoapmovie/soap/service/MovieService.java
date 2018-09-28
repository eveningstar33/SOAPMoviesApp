package com.dgs.springbootsoapmovie.soap.service;

import java.util.List;

import com.dgs.springbootsoapmovie.soap.bean.Movie;

public interface MovieService {

	public Movie getMovieById(int id);
	public List<Movie> getAllMovies();
}
