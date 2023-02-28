package com.haliltprkk.movieapplication.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haliltprkk.movieapplication.R
import com.haliltprkk.movieapplication.common.utils.Resource
import com.haliltprkk.movieapplication.common.utils.UiText
import com.haliltprkk.movieapplication.domain.models.Movie
import com.haliltprkk.movieapplication.domain.use_cases.search.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchMovieUseCase: SearchMovieUseCase) : ViewModel() {
    private val _state = MutableStateFlow<SearchViewState>(SearchViewState.Init)
    fun getViewState(): StateFlow<SearchViewState> = _state.asStateFlow()
    private var searchJob: Job? = null

    fun setLoading(isLoading: Boolean) {
        _state.value = SearchViewState.Loading(isLoading)
    }

    fun searchMovie(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            searchMovieUseCase.searchMovie(query).onEach {
                when (it) {
                    is Resource.Error -> {
                        setLoading(false)
                        _state.value = SearchViewState.Error(it.message)
                    }
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        setLoading(false)
                        if (!it.data.isNullOrEmpty()) {
                            _state.value = SearchViewState.Success(it.data)
                        } else {
                            _state.value = SearchViewState.Error(
                                UiText.StringResource(R.string.searchPage_noMovieText)
                            )
                        }
                    }
                }
            }.launchIn(this)
        }
    }

    sealed class SearchViewState {
        object Init : SearchViewState()
        data class Loading(val isLoading: Boolean) : SearchViewState()
        data class Success(val data: ArrayList<Movie>) : SearchViewState()
        data class Error(val error: UiText) : SearchViewState()
    }
}
