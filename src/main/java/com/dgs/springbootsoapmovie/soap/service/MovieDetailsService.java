package com.dgs.springbootsoapmovie.soap.service;

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

}
