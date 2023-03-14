package com.meadetechnologies.popularmoviesapp.data.remote

import com.meadetechnologies.popularmoviesapp.data.model.Movie

interface MovieRemoteDataSource {
    suspend fun getMovies(): List<Movie>
}
