package com.example.popularmoviesapp;

import com.example.popularmoviesapp.Database.JsonResponse;
import com.example.popularmoviesapp.Database.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceholderApi {

    @GET("3/movie/top_rated")
    Call<JsonResponse> getMovies(@Query("api_key") String apiKey);
}
