package com.meadetechnologies.popularmoviesapp.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.meadetechnologies.popularmoviesapp.R
import com.meadetechnologies.popularmoviesapp.databinding.FragmentMovieDetailBinding

class MovieDetailFragment : Fragment() {

    private lateinit var binding : FragmentMovieDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false)
        return binding.root
    }

}
