package com.haliltprkk.movieapplication.presentation.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.haliltprkk.movieapplication.common.extension.toFullImageLink
import com.haliltprkk.movieapplication.databinding.ListItemMovieBinding
import com.haliltprkk.movieapplication.domain.model.Movie


class MovieAdapter(
    private val listener: CharacterItemListener
) : RecyclerView.Adapter<MovieViewHolder>() {

    private val data = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ListItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding, listener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Movie>) {
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
    val binding: ListItemMovieBinding,
    private val listener: CharacterItemListener
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private lateinit var movie: Movie

    init {
        binding.root.setOnClickListener(this)
    }

    fun bind(item: Movie) {
        this.movie = item
        binding.tvTitle.text = item.title
        Glide.with(itemView.context).load(item.posterPath.toFullImageLink()).into(binding.ivMovie)
        binding.tvOverview.text = item.overview
    }

    override fun onClick(v: View?) {
        listener.onMovieClicked(movie.id)
    }
}

interface CharacterItemListener {
    fun onMovieClicked(movieId: Int)
}