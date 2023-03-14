package com.meadetechnologies.popularmoviesapp.ui.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.meadetechnologies.popularmoviesapp.R
import com.meadetechnologies.popularmoviesapp.databinding.FragmentMovieListBinding

class MovieListFragment : Fragment() {

    private lateinit var binding : FragmentMovieListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        binding.nextFragmentButton.setOnClickListener {
            val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }

}
