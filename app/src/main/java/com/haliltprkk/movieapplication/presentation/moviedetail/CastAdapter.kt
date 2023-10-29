package com.haliltprkk.movieapplication.presentation.moviedetail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haliltprkk.movieapplication.databinding.ListItemCastBinding
import com.haliltprkk.movieapplication.domain.models.Movie

class CastAdapter : RecyclerView.Adapter<ViewModel>() {

    private val data = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModel {
        val binding = ListItemCastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewModel(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Movie>) {
        this.data.clear()
        this.data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewModel, position: Int) = with(holder) {
        bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}

class ViewModel(
    private val binding: ListItemCastBinding
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var movie: Movie

    fun bind(item: Movie) {
        this.movie = item
    }
}
