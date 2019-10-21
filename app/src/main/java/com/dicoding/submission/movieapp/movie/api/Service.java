package com.dicoding.submission.movieapp.movie.api;

import com.dicoding.submission.movieapp.movie.model.Movie;
import com.dicoding.submission.movieapp.movie.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {
    @GET("discover/movie")
    Call<MoviesResponse> getMovie(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("movie/{movie_id}")
    Call<Movie> getMovieDetail(@Path("movie_id") String movieId, @Query("api_key") String apiKey, @Query("language") String language);

    @GET("discover/tv")
    Call<MoviesResponse> getTvShow(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("search/movie")
    Call<MoviesResponse> getMoviesSearch(@Query("api_key") String apiKey, @Query("language") String language, @Query("query") String query);

    @GET("search/tv")
    Call<MoviesResponse> getTvshowSearch(@Query("api_key") String apiKey, @Query("language") String language, @Query("query") String query);

    @GET("discover/movie")
    Call<MoviesResponse> getTodayRelease(@Query("api_key") String apiKey, @Query("primary_release_date.gte") String dateGte, @Query("primary_release_date.lte") String dateLte);

}
