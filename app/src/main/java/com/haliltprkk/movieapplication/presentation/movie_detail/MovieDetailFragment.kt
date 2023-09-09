package com.haliltprkk.movieapplication.presentation.movie_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.haliltprkk.movieapplication.R
import com.haliltprkk.movieapplication.common.extension.runTimeToReadableDuration
import com.haliltprkk.movieapplication.common.extension.toFullImageLink
import com.haliltprkk.movieapplication.common.utils.Constants.Companion.ARG_ID
import com.haliltprkk.movieapplication.common.utils.UiText
import com.haliltprkk.movieapplication.databinding.FragmentMovieDetailBinding
import com.haliltprkk.movieapplication.domain.models.Movie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

const val DEFAULT_MOVIE_ID = 0L

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private val viewModel: MovieDetailViewModel by viewModels()
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private val movieId: Long by lazy { arguments?.getLong(ARG_ID) ?: DEFAULT_MOVIE_ID }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeners()
        setupObservers()
        init()
    }

    private fun setupObservers() {
        viewModel.getViewState().flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }.launchIn(lifecycleScope)
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

    private fun init() = viewModel.getMovie(id = movieId)

    private fun listeners() = binding.ivBack.setOnClickListener { findNavController().popBackStack() }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
