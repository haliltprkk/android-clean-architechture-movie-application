package com.haliltprkk.movieapplication.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haliltprkk.movieapplication.common.utils.Resource
import com.haliltprkk.movieapplication.common.utils.UiText
import com.haliltprkk.movieapplication.domain.models.Movie
import com.haliltprkk.movieapplication.domain.use_cases.home.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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
        viewModelScope.launch {
            getMoviesUseCase.getPopularMovies(page).onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        setLoading(false)
                        _state.value = HomeViewState.Error(result.message)
                    }
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        setLoading(false)
                        if (result.data == null || result.data.size == 0) {
                            _state.value = HomeViewState.SuccessWithEmptyData
                        } else {
                            _state.value = HomeViewState.Success(result.data)
                        }
                    }
                }
            }.launchIn(this)
        }
    }

    sealed class HomeViewState {
        object Init : HomeViewState()
        data class Loading(val isLoading: Boolean) : HomeViewState()
        data class Success(val data: ArrayList<Movie>) : HomeViewState()
        object SuccessWithEmptyData : HomeViewState()
        data class Error(val error: UiText) : HomeViewState()
    }
}
