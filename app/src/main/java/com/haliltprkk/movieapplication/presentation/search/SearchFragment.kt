package com.haliltprkk.movieapplication.presentation.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haliltprkk.movieapplication.common.base.BaseFragment
import com.haliltprkk.movieapplication.common.extension.addSimpleVerticalDecoration
import com.haliltprkk.movieapplication.common.extension.observeInLifecycle
import com.haliltprkk.movieapplication.common.utils.UiText
import com.haliltprkk.movieapplication.databinding.FragmentSearchBinding
import com.haliltprkk.movieapplication.domain.models.Movie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: SearchMovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etSearch.requestFocus()
        setUpList()
        listeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getViewState().observeInLifecycle(lifecycle, ::handleStateChange)
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
        binding.viewStubError.visibility = View.GONE
        adapter.setItems(data)
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun handleError(error: UiText) {
        adapter.setItems(arrayListOf())
        val viewStubInflated = binding.viewStubError.inflate() as TextView
        viewStubInflated.text = error.asString(requireContext())
    }

    private fun listeners() {
        binding.ivBack.setOnClickListener { findNavController().popBackStack() }
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideSoftKeyboard()
            }
            true
        }
        binding.etSearch.addTextChangedListener { text ->
            text?.toString()?.let { viewModel.searchMovie(it) }
        }
    }

    private fun setUpList() {
        binding.rvMovies.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvMovies.addSimpleVerticalDecoration(
            16,
            includeFirstItem = true,
            includeLastItem = true
        )
        adapter = SearchMovieAdapter(object : MovieItemListener {
            override fun onMovieClicked(movieId: Long) {
                val action = SearchFragmentDirections.actionSearchFragmentToMovieDetailFragment(movieId)
                findNavController().navigate(action)
            }
        })
        binding.rvMovies.adapter = adapter
    }

    private fun hideSoftKeyboard() {
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager.isAcceptingText) {
            inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        }
    }

    override fun onDestroyView() {
        binding.rvMovies.adapter = null
        super.onDestroyView()
    }
}
