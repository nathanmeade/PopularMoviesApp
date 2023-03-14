package com.meadetechnologies.popularmoviesapp.data.repository

import com.meadetechnologies.popularmoviesapp.data.local.MovieLocalDataSource
import com.meadetechnologies.popularmoviesapp.data.remote.MovieRemoteDataSource

class MovieRepository(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource
) { /* ... */ }
