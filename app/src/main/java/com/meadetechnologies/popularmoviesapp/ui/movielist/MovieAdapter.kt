package com.meadetechnologies.popularmoviesapp.ui.movielist

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import com.meadetechnologies.popularmoviesapp.R
import com.meadetechnologies.popularmoviesapp.data.model.Movie

class MovieAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

//    var data: MutableList<Movie> = mutableListOf()
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
        val bundle = Bundle()
        bundle.putInt("id", movies[position].id)
        holder.itemView.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_movieListFragment_to_movieDetailFragment, bundle)
        )
    }

    override fun getItemCount() = movies.size

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Movie) = with(itemView) {
            itemView.findViewById<TextView>(R.id.idTextView).text = item.id.toString()
//            itemView.findViewById<TextView>(R.id.idTextView).setOnClickListener {  }
//            itemView.findViewById<TextView>(R.id.idTextView).text = "test"
            itemView.setOnClickListener {

            }
        }
    }
}
