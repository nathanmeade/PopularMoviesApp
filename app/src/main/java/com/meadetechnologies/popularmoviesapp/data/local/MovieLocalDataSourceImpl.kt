package com.meadetechnologies.popularmoviesapp.data.local

import com.meadetechnologies.popularmoviesapp.data.model.Movie

class MovieLocalDataSourceImpl(private val movieDao: MovieDao) : MovieLocalDataSource {
    override suspend fun getMovies(): List<Movie> {
        return movieDao.getAllMovie().value ?: listOf(Movie(5))
    }
}
