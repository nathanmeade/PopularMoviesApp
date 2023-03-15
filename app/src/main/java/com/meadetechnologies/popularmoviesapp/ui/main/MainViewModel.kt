package com.meadetechnologies.popularmoviesapp.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meadetechnologies.popularmoviesapp.data.local.MovieDatabase
import com.meadetechnologies.popularmoviesapp.data.local.MovieLocalDataSourceImpl
import com.meadetechnologies.popularmoviesapp.data.model.Movie
import com.meadetechnologies.popularmoviesapp.data.remote.ApiService
import com.meadetechnologies.popularmoviesapp.data.remote.MovieRemoteDataSourceImpl
import com.meadetechnologies.popularmoviesapp.data.repository.MovieRepository
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel(application: Application, val apiKey: String) : ViewModel() {

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

    private val _movies = MutableLiveData<MutableList<Movie>>()

    val movies : LiveData<MutableList<Movie>>
        get() = _movies

    init {
        _movies.value = mutableListOf(Movie(87, "https://api.themoviedb.org/3/movie/3bhkrj58Vtu7enYsRolD1fZdja1.jpg"))
//        viewModelScope.launch {
//            movieRepository.refreshMovies(apiKey)
//        }

    }

    suspend fun setMovies() {
//        return listOf(Movie(76))
        var mutableList = mutableListOf<Movie>()
        movieRepository.getMovies(apiKey).forEach {
            mutableList.add(it)
        }
//        _movies.value= mutableList
        _movies.postValue(mutableList)
    }

//    suspend fun getMovies(): LiveData<List<Movie>> {
////        return listOf(Movie(76))
//        return movieRepository.getMovies(apiKey)
//    }
}
