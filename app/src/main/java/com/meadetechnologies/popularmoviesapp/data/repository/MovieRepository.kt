package com.meadetechnologies.popularmoviesapp.data.repository

import android.util.Log
import com.meadetechnologies.popularmoviesapp.data.local.MovieLocalDataSource
import com.meadetechnologies.popularmoviesapp.data.model.Movie
import com.meadetechnologies.popularmoviesapp.data.remote.MovieRemoteDataSource

class MovieRepository(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource
) {

    var moviesList: List<Movie> = listOf()

    //    suspend fun getMovies(apiKey: String) : List<Movie> {
//        //return movieLocalDataSource.getMovies()
//        return movieRemoteDataSource.getMovies(apiKey)
////        return listOf(Movie(6))
//    }
    suspend fun refreshMovies(apiKey: String) {
        try {
            val movies = movieRemoteDataSource.getMovies(apiKey)
            movieLocalDataSource.saveMovies(movies)
            moviesList = movies
//            movieLocalDataSource.getMovies()
        } catch (e: Exception) {
            Log.e("nathanTest", "Exception: $e")
//            val movies = movieLocalDataSource.getMovies()
//            movies
            moviesList = movieLocalDataSource.getMovies()
        }
    }

    suspend fun getMovies(apiKey: String): List<Movie> {
        return try {
            val movies = movieRemoteDataSource.getMovies(apiKey)
            movieLocalDataSource.saveMovies(movies)
            movies
//            movieLocalDataSource.getMovies()
        } catch (e: Exception) {
            Log.e("nathanTest", "Exception: $e")
//            val movies = movieLocalDataSource.getMovies()
//            movies
            movieLocalDataSource.getMovies()
        }
    }
}
