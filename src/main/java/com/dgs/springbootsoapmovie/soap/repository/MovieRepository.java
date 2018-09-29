package com.dgs.springbootsoapmovie.soap.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.dgs.springbootsoapmovie.soap.bean.Movie;

public interface MovieRepository extends CrudRepository<Movie, Integer> {

	public Movie findMovieById(int id);
	public List<Movie> findMovieByName(String name); 
}
