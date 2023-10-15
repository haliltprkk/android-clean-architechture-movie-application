package com.haliltprkk.movieapplication.presentation.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haliltprkk.movieapplication.common.base.BaseFragment
import com.haliltprkk.movieapplication.common.extension.addSimpleVerticalDecoration
import com.haliltprkk.movieapplication.common.utils.UiText
import com.haliltprkk.movieapplication.databinding.FragmentHomeBinding
import com.haliltprkk.movieapplication.domain.models.Movie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: MovieAdapter
    private val page = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeners()
        setUpList()
        setupObservers()
        viewModel.getMovies(page)
    }

    private fun listeners() =
        binding.cvSearch.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            findNavController().navigate(action)
        }

    private fun setupObservers() {
        viewModel.getViewState().flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }.launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: HomeViewModel.HomeViewState) {
        when (state) {
            is HomeViewModel.HomeViewState.Init -> Unit
            is HomeViewModel.HomeViewState.Loading -> handleLoading(state.isLoading)
            is HomeViewModel.HomeViewState.Success -> handleSuccess(state.data)
            is HomeViewModel.HomeViewState.SuccessWithEmptyData -> Unit
            is HomeViewModel.HomeViewState.Error -> handleError(state.error)
        }
    }

    private fun handleSuccess(list: List<Movie>) = adapter.setItems(list)

    private fun handleLoading(loading: Boolean) {
        binding.progressBar.isVisible = loading
    }

    private fun handleError(error: UiText) =
        Toast.makeText(requireContext(), error.asString(requireContext()), Toast.LENGTH_SHORT).show()

    private fun setUpList() {
        binding.rvMovies.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        )
        binding.rvMovies.addSimpleVerticalDecoration(
            16,
            includeFirstItem = true,
            includeLastItem = true
        )
        adapter = MovieAdapter(object : MovieItemListener {

            override fun onMovieClicked(movieId: Long) {
                val action = HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(movieId)
                findNavController().navigate(action)
            }
        })
        binding.rvMovies.adapter = adapter
    }

    override fun onDestroyView() {
        binding.rvMovies.adapter = null
        super.onDestroyView()
    }
}
