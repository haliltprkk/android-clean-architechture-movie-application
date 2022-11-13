package com.haliltprkk.movieapplication.presentation.search

import androidx.lifecycle.ViewModel
import com.haliltprkk.movieapplication.common.UiText
import com.haliltprkk.movieapplication.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<SearchViewState>(SearchViewState.Init)
    fun getViewState(): StateFlow<SearchViewState> = _state.asStateFlow()

    init {
        _state.value = SearchViewState.Success(
            arrayListOf(
                Movie.getMockMovie(),
                Movie.getMockMovie(),
                Movie.getMockMovie(),
                Movie.getMockMovie(),
                Movie.getMockMovie()
            )
        )
    }

    sealed class SearchViewState {
        object Init : SearchViewState()
        data class IsLoading(val isLoading: Boolean) : SearchViewState()
        data class Success(val data: ArrayList<Movie>) : SearchViewState()
        data class Error(val error: UiText) : SearchViewState()
    }
}
