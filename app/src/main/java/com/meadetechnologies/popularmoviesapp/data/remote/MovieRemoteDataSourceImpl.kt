package com.meadetechnologies.popularmoviesapp.data.remote

import com.meadetechnologies.popularmoviesapp.data.model.Movie

class MovieRemoteDataSourceImpl(private val apiService: ApiService) : MovieRemoteDataSource {
    override suspend fun getMovies(apiKey: String): List<Movie> {
        val response = apiService.getPopularMovies(apiKey)
        return if (response.isSuccessful) {
            response.body()?.results ?: emptyList()
        } else {
            emptyList()
        }
    }
}
