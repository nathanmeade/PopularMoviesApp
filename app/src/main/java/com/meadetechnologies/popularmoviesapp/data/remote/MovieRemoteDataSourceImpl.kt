package com.meadetechnologies.popularmoviesapp.data.remote

import com.meadetechnologies.popularmoviesapp.data.model.Movie

class MovieRemoteDataSourceImpl(private val apiService: ApiService) : MovieRemoteDataSource {
    override suspend fun getMovies(): List<Movie> {
        return apiService.getMovies()
    }
}
