package com.haliltprkk.movieapplication.presentation.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haliltprkk.movieapplication.common.UiText
import com.haliltprkk.movieapplication.common.extension.addSimpleVerticalDecoration
import com.haliltprkk.movieapplication.data.services.localStorage.LocalStorageService
import com.haliltprkk.movieapplication.databinding.ActivityHomeBinding
import com.haliltprkk.movieapplication.domain.models.Movie
import com.haliltprkk.movieapplication.presentation.movie_detail.MovieDetailActivity
import com.haliltprkk.movieapplication.presentation.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: MovieAdapter

    @Inject
    lateinit var localStorageService: LocalStorageService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listeners()
        setUpList()
        setupObservers()
        localStorageService.setSample("lorem")
        val asd = localStorageService.getSample()
        val asdx = ""
    }

    private fun listeners() =
        binding.cvSearch.setOnClickListener { startActivity(SearchActivity.createSimpleIntent(this)) }

    private fun setupObservers() {
        viewModel.getViewState().flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }.launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: HomeViewModel.HomeViewState) {
        when (state) {
            is HomeViewModel.HomeViewState.Init -> Unit
            is HomeViewModel.HomeViewState.IsLoading -> handleLoading(state.isLoading)
            is HomeViewModel.HomeViewState.Success -> handleSuccess(state.data)
            is HomeViewModel.HomeViewState.SuccessWithEmptyData -> Unit
            is HomeViewModel.HomeViewState.Error -> handleError(state.error)
        }
    }

    private fun handleSuccess(list: ArrayList<Movie>) = adapter.setItems(list)

    private fun handleLoading(loading: Boolean) {
        binding.progressBar.isVisible = loading
    }

    private fun handleError(error: UiText) = Toast.makeText(this, error.asString(this), Toast.LENGTH_SHORT).show()

    private fun setUpList() {
        binding.rvMovies.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvMovies.addSimpleVerticalDecoration(
            16, includeFirstItem = true, includeLastItem = true
        )
        adapter = MovieAdapter(object : MovieItemListener {
            override fun onMovieClicked(movieId: Long) {
                startActivity(MovieDetailActivity.createSimpleIntent(this@HomeActivity, movieId = movieId))
            }
        })
        binding.rvMovies.adapter = adapter
    }

    companion object {
        fun createSimpleIntent(context: Context): Intent = Intent(context, HomeActivity::class.java)
    }
}
