package com.meadetechnologies.popularmoviesapp.data.local

import com.meadetechnologies.popularmoviesapp.data.model.Movie

interface MovieLocalDataSource {
    suspend fun getMovies(): List<Movie>
}
