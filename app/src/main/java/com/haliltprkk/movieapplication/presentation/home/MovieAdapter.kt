package com.haliltprkk.movieapplication.presentation.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haliltprkk.movieapplication.databinding.ListItemMovieBinding
import com.haliltprkk.movieapplication.domain.model.MovieItem


class MovieAdapter(
    private val listener: CharacterItemListener
) : RecyclerView.Adapter<MovieViewHolder>() {

    private val data = ArrayList<MovieItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ListItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding, listener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<MovieItem>) {
        this.data.clear()
        this.data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        with(holder) { bind(data[position]) }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class MovieViewHolder(
    itemBinding: ListItemMovieBinding,
    private val listener: CharacterItemListener
) : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

    private lateinit var movie: MovieItem

    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(item: MovieItem) {
        this.movie = item
    }

    override fun onClick(v: View?) {
        listener.onMovieClicked(movie.id)
    }
}

interface CharacterItemListener {
    fun onMovieClicked(movieId: String)
}