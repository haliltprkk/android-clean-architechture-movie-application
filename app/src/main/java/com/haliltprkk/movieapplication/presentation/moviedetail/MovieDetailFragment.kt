package com.haliltprkk.movieapplication.presentation.moviedetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.haliltprkk.movieapplication.R
import com.haliltprkk.movieapplication.common.base.ViewBindingFragment
import com.haliltprkk.movieapplication.common.extension.observeInLifecycle
import com.haliltprkk.movieapplication.common.extension.runTimeToReadableDuration
import com.haliltprkk.movieapplication.common.extension.toFullImageLink
import com.haliltprkk.movieapplication.common.utils.UiText
import com.haliltprkk.movieapplication.databinding.FragmentMovieDetailBinding
import com.haliltprkk.movieapplication.domain.models.Cast
import com.haliltprkk.movieapplication.domain.models.Movie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : ViewBindingFragment<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate) {
    private val viewModel: MovieDetailViewModel by viewModels()
    private val args: MovieDetailFragmentArgs by navArgs()
    private lateinit var castsAdapter: CastAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeners()
        setupObservers()
        init()
    }

    private fun setupObservers() {
        viewModel.getViewState().observeInLifecycle(lifecycle, ::handleStateChange)
    }

    private fun handleStateChange(state: MovieDetailViewModel.MovieDetailViewState) {
        when (state) {
            is MovieDetailViewModel.MovieDetailViewState.Error -> handleError(state.error)
            MovieDetailViewModel.MovieDetailViewState.Init -> MovieDetailViewModel.MovieDetailViewState.Init
            is MovieDetailViewModel.MovieDetailViewState.Loading -> handleLoading()
            is MovieDetailViewModel.MovieDetailViewState.Success -> handleSuccess(state.movie, state.castList)
        }
    }

    private fun handleSuccess(data: Movie, castList: List<Cast>) {
        binding.progress.isVisible = false
        binding.tvMovieTitle.text = data.title
        binding.tvDescription.text = data.overview
        binding.tvGenre.text = data.genre
        binding.tvDuration.text = data.runtime.runTimeToReadableDuration(requireContext())
        binding.tvRating.text = getString(R.string.ratingWithParam, data.voteAverage)
        Glide.with(this).load(data.posterPath.toFullImageLink()).into(binding.ivMovie)
        if (castList.isNotEmpty()) {
            castsAdapter.setItems(castList)
        }
    }

    private fun handleLoading() {
        binding.progress.isVisible = true
    }

    private fun handleError(error: UiText) {
        binding.progress.isVisible = false
        Toast.makeText(
            requireContext(),
            error.asString(requireContext()),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun init() {
        setUpList()
        viewModel.getMovieDetails(id = args.movieId)
    }

    private fun setUpList() {
        binding.rvCasts.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )
        castsAdapter = CastAdapter()
        binding.rvCasts.adapter = castsAdapter
    }

    private fun listeners() = binding.ivBack.setOnClickListener { findNavController().popBackStack() }
}
