package com.meadetechnologies.popularmoviesapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.meadetechnologies.popularmoviesapp.R

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val factory = MainViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

    }
}
