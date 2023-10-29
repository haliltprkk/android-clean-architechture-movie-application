package com.haliltprkk.movieapplication.presentation.moviedetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.haliltprkk.movieapplication.R
import com.haliltprkk.movieapplication.common.base.ViewBindingFragment
import com.haliltprkk.movieapplication.common.extension.observeInLifecycle
import com.haliltprkk.movieapplication.common.extension.runTimeToReadableDuration
import com.haliltprkk.movieapplication.common.extension.toFullImageLink
import com.haliltprkk.movieapplication.common.utils.UiText
import com.haliltprkk.movieapplication.databinding.FragmentMovieDetailBinding
import com.haliltprkk.movieapplication.domain.models.Movie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : ViewBindingFragment<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate) {
    private val viewModel: MovieDetailViewModel by viewModels()
    private val args: MovieDetailFragmentArgs by navArgs()

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
            is MovieDetailViewModel.MovieDetailViewState.Loading -> handleLoading(state.isLoading)
            is MovieDetailViewModel.MovieDetailViewState.Success -> handleSuccess(state.data)
        }
    }

    private fun handleSuccess(data: Movie) {
        binding.tvMovieTitle.text = data.title
        binding.tvDescription.text = data.overview
        binding.tvGenre.text = data.genre
        binding.tvDuration.text = data.runtime.runTimeToReadableDuration(requireContext())
        binding.tvRating.text = getString(R.string.ratingWithParam, data.voteAverage)
        Glide.with(this).load(data.posterPath.toFullImageLink()).into(binding.ivMovie)
    }

    private fun handleLoading(loading: Boolean) {
        binding.progress.isVisible = loading
    }

    private fun handleError(error: UiText) = Toast.makeText(
        requireContext(),
        error.asString(requireContext()),
        Toast.LENGTH_SHORT
    ).show()

    private fun init() = viewModel.getMovie(id = args.movieId)

    private fun listeners() = binding.ivBack.setOnClickListener { findNavController().popBackStack() }
}
