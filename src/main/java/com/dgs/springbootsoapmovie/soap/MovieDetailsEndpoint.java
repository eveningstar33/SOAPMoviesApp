package com.dgs.springbootsoapmovie.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.dgs.springbootsoapmovie.soap.bean.Movie;
import com.dgs.springbootsoapmovie.soap.service.MovieDetailsService;
import com.usdgadget.movies.GetMovieDetailsRequest;
import com.usdgadget.movies.GetMovieDetailsResponse;
import com.usdgadget.movies.MovieDetails;

@Endpoint
public class MovieDetailsEndpoint {

	@Autowired
	private MovieDetailsService service;
	
	// We have created a method which is accepting GetMovieDetailsRequest as input and the output is GetMovieDetailsResponse.
	// We have configured it to accept requests of namespace http://usdgadget.com/movies and the name GetMovieDetailsRequest by 
	// configuring at @PayloadRoot. We said, ok this is the request, you need to convert it from XML to Java and we use @RequestPayload.
	// And we also configure @ResponsePayload to do the reverse.
	
	// The method annotated with @PayloadRoot becomes an Endpoint method that accepts incoming request and returns response. 
	
	@PayloadRoot(namespace = "http://usdgadget.com/movies", localPart = "GetMovieDetailsRequest")
	@ResponsePayload
	public GetMovieDetailsResponse processMovieDetailsRequest(@RequestPayload GetMovieDetailsRequest request) {
				
		Movie movie = service.getMovieById(request.getId());  
						
		return mapMovieDetails(movie); 
	}
	
	private GetMovieDetailsResponse mapMovieDetails(Movie movie) {
		
		GetMovieDetailsResponse response = new GetMovieDetailsResponse();
		
		response.setMovieDetails(mapMovie(movie)); 
		
		return response;
	}
	
	private MovieDetails mapMovie(Movie movie) {
		
		MovieDetails movieDetails = new MovieDetails();
						
		movieDetails.setId(movie.getId());
		
		movieDetails.setName(movie.getName()); 
		
		movieDetails.setGenre(movie.getGenre()); 
				
		return movieDetails;
	}
}
