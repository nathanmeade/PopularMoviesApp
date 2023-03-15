package com.meadetechnologies.popularmoviesapp.data.repository

import com.meadetechnologies.popularmoviesapp.data.local.MovieLocalDataSource
import com.meadetechnologies.popularmoviesapp.data.model.Movie
import com.meadetechnologies.popularmoviesapp.data.remote.MovieRemoteDataSource

class MovieRepository(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource
) {
    suspend fun getMovies(apiKey: String) : List<Movie> {
        //return movieLocalDataSource.getMovies()
        return movieRemoteDataSource.getMovies(apiKey)
//        return listOf(Movie(6))
    }
}
