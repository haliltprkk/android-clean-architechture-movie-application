package com.haliltprkk.movieapplication.presentation.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haliltprkk.movieapplication.common.extension.addSimpleVerticalDecoration
import com.haliltprkk.movieapplication.common.utils.UiText
import com.haliltprkk.movieapplication.databinding.ActivitySearchBinding
import com.haliltprkk.movieapplication.domain.models.Movie
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
        binding.etSearch.requestFocus()
        setUpList()
        listeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getViewState().flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }.launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: SearchViewModel.SearchViewState) {
        when (state) {
            is SearchViewModel.SearchViewState.Error -> handleError(state.error)
            SearchViewModel.SearchViewState.Init -> Unit
            is SearchViewModel.SearchViewState.Loading -> handleLoading(state.isLoading)
            is SearchViewModel.SearchViewState.Success -> handleSuccess(state.data)
        }
    }

    private fun handleSuccess(data: List<Movie>) {
        binding.viewError.tvError.visibility = View.GONE
        adapter.setItems(data)
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun handleError(error: UiText) {
        binding.viewError.tvError.visibility = View.VISIBLE
        adapter.setItems(arrayListOf())
        binding.viewError.tvError.text = error.asString(this)
    }

    private fun listeners() {
        binding.ivBack.setOnClickListener { finish() }
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideSoftKeyboard()
            }
            true
        }
        binding.etSearch.addTextChangedListener { text ->
            if (text != null && text.length > 1) {
                viewModel.searchMovie(text.toString())
            }
        }
    }

    private fun setUpList() {
        binding.rvMovies.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvMovies.addSimpleVerticalDecoration(
            16,
            includeFirstItem = true,
            includeLastItem = true
        )
        adapter = SearchMovieAdapter(object : MovieItemListener {
            override fun onMovieClicked(movieId: Long) {
                // TODO fix here later
//                startActivity(
//                    MovieDetailActivity.createSimpleIntent(
//                        this@SearchActivity,
//                        movieId = movieId
//                    )
//                )
            }
        })
        binding.rvMovies.adapter = adapter
    }

    private fun hideSoftKeyboard() {
        val inputMethodManager: InputMethodManager = getSystemService(
            INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (inputMethodManager.isAcceptingText) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    companion object {
        fun createSimpleIntent(context: Context): Intent = Intent(
            context,
            SearchActivity::class.java
        )
    }
}
