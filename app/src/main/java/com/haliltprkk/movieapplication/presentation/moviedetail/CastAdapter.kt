package com.haliltprkk.movieapplication.presentation.moviedetail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.haliltprkk.movieapplication.common.extension.toFullImageLink
import com.haliltprkk.movieapplication.databinding.ListItemCastBinding
import com.haliltprkk.movieapplication.domain.models.Cast

class CastAdapter : RecyclerView.Adapter<ViewModel>() {

    private val data = ArrayList<Cast>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModel {
        val binding = ListItemCastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewModel(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(castList: List<Cast>) {
        this.data.clear()
        this.data.addAll(castList)
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

    fun bind(item: Cast) {
        binding.tvCharacterName.text = item.character
        binding.tvActorName.text = item.originalName
        Glide.with(itemView.context).load(item.profilePath.toFullImageLink()).into(binding.ivActor)
    }
}
