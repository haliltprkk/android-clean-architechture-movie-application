package com.haliltprkk.movieapplication.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haliltprkk.movieapplication.common.utils.Resource
import com.haliltprkk.movieapplication.common.utils.UiText
import com.haliltprkk.movieapplication.domain.models.Movie
import com.haliltprkk.movieapplication.domain.usecases.home.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<HomeViewState>(HomeViewState.Init)
    fun getViewState(): StateFlow<HomeViewState> = _state.asStateFlow()

    fun setLoading(isLoading: Boolean) {
        _state.value = HomeViewState.Loading(isLoading)
    }

    fun getMovies(page: Int) {
        if (_state.value is HomeViewState.Success) return

        viewModelScope.launch {
            getMoviesUseCase.getPopularMovies(page).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        setLoading(false)
                        _state.value = HomeViewState.Error(result.message)
                    }

                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        setLoading(false)
                        if (result.data.isNullOrEmpty()) {
                            _state.value = HomeViewState.SuccessWithEmptyData
                        } else {
                            _state.value = HomeViewState.Success(result.data)
                        }
                    }
                }
            }
        }
    }

    sealed class HomeViewState {
        object Init : HomeViewState()
        data class Loading(val isLoading: Boolean) : HomeViewState()
        data class Success(val data: List<Movie>) : HomeViewState()
        object SuccessWithEmptyData : HomeViewState()
        data class Error(val error: UiText) : HomeViewState()
    }
}
