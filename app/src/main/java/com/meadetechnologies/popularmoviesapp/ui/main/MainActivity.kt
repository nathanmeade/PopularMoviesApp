package com.meadetechnologies.popularmoviesapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.meadetechnologies.popularmoviesapp.BuildConfig
import com.meadetechnologies.popularmoviesapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val apiKey = "" + BuildConfig.API_KEY + ""
        Log.d("nathanTest", "onCreate: $apiKey")
        val factory = MainViewModelFactory(application, apiKey)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        val myScope = CoroutineScope(Dispatchers.IO)
        myScope.launch {
            Log.d("nathanTest", "onCreate: ${viewModel.movies}")
        }

    }
}
