package com.meadetechnologies.popularmoviesapp.data.remote

import com.meadetechnologies.popularmoviesapp.data.model.Movie
import retrofit2.http.GET

interface ApiService {
    @GET("movies")
    suspend fun getMovies(): List<Movie>
}
