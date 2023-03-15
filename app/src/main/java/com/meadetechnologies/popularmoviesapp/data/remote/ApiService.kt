package com.meadetechnologies.popularmoviesapp.data.remote

import com.meadetechnologies.popularmoviesapp.data.model.Movie
import com.meadetechnologies.popularmoviesapp.data.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movies")
    suspend fun getMovies(): List<Movie>

    @GET("popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): Response<MovieResponse>
}
