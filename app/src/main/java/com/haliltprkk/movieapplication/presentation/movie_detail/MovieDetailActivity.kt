package com.haliltprkk.movieapplication.presentation.movie_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.haliltprkk.movieapplication.R
import com.haliltprkk.movieapplication.common.extension.runTimeToReadableDuration
import com.haliltprkk.movieapplication.common.extension.toFullImageLink
import com.haliltprkk.movieapplication.common.utils.Constants.Companion.ARG_ID
import com.haliltprkk.movieapplication.common.utils.UiText
import com.haliltprkk.movieapplication.databinding.ActivityMovieDetailBinding
import com.haliltprkk.movieapplication.domain.models.Movie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.extras?.getLong(ARG_ID)?.let { MOVIE_ID = it }
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
        binding.tvDuration.text = data.runtime.runTimeToReadableDuration(this)
        binding.tvRating.text = getString(R.string.ratingWithParam, data.voteAverage)
        Glide.with(this).load(data.posterPath.toFullImageLink()).into(binding.ivMovie)
    }

    private fun handleLoading(loading: Boolean) {
        binding.progress.isVisible = loading
    }

    private fun handleError(error: UiText) = Toast.makeText(
        this,
        error.asString(this),
        Toast.LENGTH_SHORT
    ).show()

    private fun init() = viewModel.getMovie(id = MOVIE_ID)

    private fun listeners() = binding.ivBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

    companion object {
        private var MOVIE_ID: Long = 0
        fun createSimpleIntent(context: Context, movieId: Long): Intent =
            Intent(context, MovieDetailActivity::class.java).putExtra(ARG_ID, movieId)
    }
}
