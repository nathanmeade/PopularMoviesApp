package com.meadetechnologies.popularmoviesapp.data.local

import com.meadetechnologies.popularmoviesapp.data.model.Movie
import kotlinx.coroutines.delay

class MovieLocalDataSourceImpl(private val movieDao: MovieDao) : MovieLocalDataSource {
    override suspend fun getMovies(): List<Movie> {
        //movieDao.addMovie(Movie(4))
//        delay(2000)
        return movieDao.getAllMovie() ?: listOf(Movie(5, "https://api.themoviedb.org/3/movie/3bhkrj58Vtu7enYsRolD1fZdja1.jpg"))
    }

    override suspend fun saveMovies(movies: List<Movie>) {
        movies.forEach { movie ->
            movieDao.addMovie(movie)
        }
    }
}
