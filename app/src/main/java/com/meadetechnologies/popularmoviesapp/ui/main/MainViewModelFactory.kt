package com.meadetechnologies.popularmoviesapp.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.meadetechnologies.popularmoviesapp.data.repository.MovieRepository

class MainViewModelFactory(private val application: Application, private val apiKey: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(application, apiKey) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
