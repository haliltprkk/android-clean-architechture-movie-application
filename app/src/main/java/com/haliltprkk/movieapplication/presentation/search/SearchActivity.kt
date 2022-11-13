package com.haliltprkk.movieapplication.presentation.search

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haliltprkk.movieapplication.common.UiText
import com.haliltprkk.movieapplication.common.extension.addSimpleVerticalDecoration
import com.haliltprkk.movieapplication.databinding.ActivitySearchBinding
import com.haliltprkk.movieapplication.domain.model.Movie
import com.haliltprkk.movieapplication.presentation.movie_detail.MovieDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: SearchMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpList()
        listeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getViewState()
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: SearchViewModel.SearchViewState) {
        when (state) {
            is SearchViewModel.SearchViewState.Error -> handleError(state.error)
            SearchViewModel.SearchViewState.Init -> Unit
            is SearchViewModel.SearchViewState.IsLoading -> handleLoading(state.isLoading)
            is SearchViewModel.SearchViewState.Success -> handleSuccess(state.data)
        }
    }

    private fun handleSuccess(data: ArrayList<Movie>) {
        adapter.setItems(data)
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun handleError(error: UiText) {
    }

    private fun listeners() {
        binding.ivBack.setOnClickListener { finish() }
    }

    private fun setUpList() {
        binding.rvMovies.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvMovies.addSimpleVerticalDecoration(
            16,
            includeFirstItem = true,
            includeLastItem = true
        )
        adapter = SearchMovieAdapter(object :
            MovieItemListener {
            override fun onMovieClicked(movieId: Long) {
                startActivity(
                    MovieDetailActivity.createSimpleIntent(
                        this@SearchActivity,
                        movieId = movieId
                    )
                )
            }
        })
        binding.rvMovies.adapter = adapter
    }

    companion object {
        fun createSimpleIntent(context: Context): Intent =
            Intent(context, SearchActivity::class.java)
    }
}
