package com.dgs.springbootsoapmovie.soap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.dgs.springbootsoapmovie.soap.bean.Movie;
import com.dgs.springbootsoapmovie.soap.exception.MovieNotFoundException;
import com.dgs.springbootsoapmovie.soap.service.MovieDetailsService;
import com.dgs.springbootsoapmovie.soap.service.MovieDetailsService.Status;
import com.usdgadget.movies.AddMovieDetailsRequest;
import com.usdgadget.movies.AddMovieDetailsResponse;
import com.usdgadget.movies.DeleteMovieDetailsRequest;
import com.usdgadget.movies.DeleteMovieDetailsResponse;
import com.usdgadget.movies.GetAllMovieDetailsRequest;
import com.usdgadget.movies.GetAllMovieDetailsResponse;
import com.usdgadget.movies.GetMovieDetailsRequest;
import com.usdgadget.movies.GetMovieDetailsResponse;
import com.usdgadget.movies.MovieDetails;
import com.usdgadget.movies.UpdateMovieDetailsRequest;
import com.usdgadget.movies.UpdateMovieDetailsResponse;

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
		
		if (movie == null) 
			throw new MovieNotFoundException("Invalid Movie Id " + request.getId());
						
		return mapMovieDetails(movie); 
	}
	
	@PayloadRoot(namespace = "http://usdgadget.com/movies", localPart = "GetAllMovieDetailsRequest")
	@ResponsePayload
	public GetAllMovieDetailsResponse processAllMovieDetailsRequest(@RequestPayload GetAllMovieDetailsRequest request) {
				
		List<Movie> movies = service.getAllMovies();  
						
		return mapAllMoviesDetails(movies); 
	}
	
	@PayloadRoot(namespace = "http://usdgadget.com/movies", localPart = "AddMovieDetailsRequest")
	@ResponsePayload
	public AddMovieDetailsResponse addMovieDetailsRequest(@RequestPayload AddMovieDetailsRequest request) {
				
		AddMovieDetailsResponse response = new AddMovieDetailsResponse(); 
		
		Movie movie = new Movie();
		movie.setName(request.getName()); 
		movie.setGenre(request.getGenre()); 
		
		Status status = service.addMovie(movie);
		
		if (status == Status.SUCCESS) {  
			response.setMovieDetails(mapMovie(movie)); 
		}
		
		response.setStatus(mapStatus(status)); 
		
		return response; 
	}
	
	@PayloadRoot(namespace = "http://usdgadget.com/movies", localPart = "UpdateMovieDetailsRequest")
	@ResponsePayload
	public UpdateMovieDetailsResponse updateMovieDetailsRequest(@RequestPayload UpdateMovieDetailsRequest request) {
				
		UpdateMovieDetailsResponse response = new UpdateMovieDetailsResponse();
		MovieDetails movieDetails = request.getMovieDetails();
		
		Movie movie = mapMovieDetails(movieDetails);
		
		service.updateMovie(movie); 
		response.setStatus(com.usdgadget.movies.Status.SUCCESS); 
						
		return response; 
	}
	
	@PayloadRoot(namespace = "http://usdgadget.com/movies", localPart = "DeleteMovieDetailsRequest")
	@ResponsePayload
	public DeleteMovieDetailsResponse deleteMovieDetailsRequest(@RequestPayload DeleteMovieDetailsRequest request) {
				
		Status status = service.deleteMovieById(request.getId());  // This status which we are getting from the service is of the type MovieDetailsService.Status				 
		DeleteMovieDetailsResponse response = new DeleteMovieDetailsResponse();
		response.setStatus(mapStatus(status));   // This status with setStatus() method uses a different status, this is actualy from 
												 // the XSD Java object.. and we need to map the status class from the service to be 
        										 // the other status class which is defined in the bean
						
		return response; 
	}
	
	private com.usdgadget.movies.Status mapStatus(Status status) { 

		if (status == Status.FAILURE) {
			return com.usdgadget.movies.Status.FAILURE;
		}
		
		return com.usdgadget.movies.Status.SUCCESS;
	}

	private GetMovieDetailsResponse mapMovieDetails(Movie movie) {
		
		GetMovieDetailsResponse response = new GetMovieDetailsResponse();
		response.setMovieDetails(mapMovie(movie)); 
		
		return response;
	}
	
	private GetAllMovieDetailsResponse mapAllMoviesDetails(List<Movie> movies) {
		
		GetAllMovieDetailsResponse response = new GetAllMovieDetailsResponse();
		for (Movie movie : movies) {
			
			MovieDetails movieDetails = mapMovie(movie);
			response.getMovieDetails().add(movieDetails); 
		}
				
		return response;
	}
	
	private MovieDetails mapMovie(Movie movie) {
		
		MovieDetails movieDetails = new MovieDetails();						
		movieDetails.setId(movie.getId());
		movieDetails.setName(movie.getName()); 
		movieDetails.setGenre(movie.getGenre()); 
				
		return movieDetails;
	}
	
	private Movie mapMovieDetails(MovieDetails movieDetails) {
		
		Movie movie = new Movie();
		movie.setId(movieDetails.getId()); 
		movie.setName(movieDetails.getName()); 
		movie.setGenre(movieDetails.getGenre());

		return movie;
	}
}
