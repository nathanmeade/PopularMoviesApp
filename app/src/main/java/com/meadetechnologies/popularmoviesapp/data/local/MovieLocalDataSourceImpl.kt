package com.meadetechnologies.popularmoviesapp.data.local

import com.meadetechnologies.popularmoviesapp.data.model.Movie
import kotlinx.coroutines.delay

class MovieLocalDataSourceImpl(private val movieDao: MovieDao) : MovieLocalDataSource {
    override suspend fun getMovies(): List<Movie> {
        //movieDao.addMovie(Movie(4))
//        delay(2000)
        return movieDao.getAllMovie().value ?: listOf(Movie(5))
    }
}
