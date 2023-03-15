package com.meadetechnologies.popularmoviesapp.ui.movielist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.meadetechnologies.popularmoviesapp.R
import com.meadetechnologies.popularmoviesapp.data.model.Movie
import com.meadetechnologies.popularmoviesapp.databinding.FragmentMovieListBinding
import com.meadetechnologies.popularmoviesapp.ui.main.MainActivity
import com.meadetechnologies.popularmoviesapp.ui.main.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class MovieListFragment : Fragment() {

    private lateinit var binding: FragmentMovieListBinding
    private lateinit var viewModel: MainViewModel

    val TAG = "aodighaoeirgh"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
//        binding.nextFragmentButton.setOnClickListener {
//            val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment()
//            findNavController().navigate(action)
//        }
//        val activity = requireActivity() as MainActivity
//        viewModel = activity.viewModel

//        binding.recyclerView.adapter.notifyDataSetChanged()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = requireActivity() as MainActivity
        viewModel = activity.viewModel
        var adapter: MovieAdapter
        adapter = MovieAdapter(listOf(Movie(7), Movie(8)))
        adapter.notifyDataSetChanged()
        val myScope = CoroutineScope(Dispatchers.IO)
        myScope.launch {
//            adapter = MovieAdapter(viewModel.getMovies())
////            adapter = MovieAdapter(listOf(Movie(67)))
//            adapter.notifyDataSetChanged()
//            binding.recyclerView.adapter = adapter
//            binding.recyclerView.adapter?.notifyDataSetChanged()
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        Log.d(TAG, "onViewCreated: ${adapter.itemCount}")
        adapter.notifyDataSetChanged()
        super.onViewCreated(view, savedInstanceState)
    }
}
