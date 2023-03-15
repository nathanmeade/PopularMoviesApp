package com.meadetechnologies.popularmoviesapp.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import com.meadetechnologies.popularmoviesapp.data.local.MovieDatabase
import com.meadetechnologies.popularmoviesapp.data.local.MovieLocalDataSourceImpl
import com.meadetechnologies.popularmoviesapp.data.model.Movie
import com.meadetechnologies.popularmoviesapp.data.remote.ApiService
import com.meadetechnologies.popularmoviesapp.data.remote.MovieRemoteDataSourceImpl
import com.meadetechnologies.popularmoviesapp.data.repository.MovieRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel(application: Application) : ViewModel() {

    private val apiService = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/movie/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    private val movieDatabase = MovieDatabase.getDatabase(application)

    private val movieDao = MovieDatabase.getDatabase(application).movieDao()

    private val remoteDataSource = MovieRemoteDataSourceImpl(apiService)
    private val localDataSource = MovieLocalDataSourceImpl(movieDao)

    private val movieRepository = MovieRepository(remoteDataSource, localDataSource)

    suspend fun getMovies(apiKey: String): List<Movie> {
//        return listOf(Movie(76))
        return movieRepository.getMovies(apiKey)
    }
}
