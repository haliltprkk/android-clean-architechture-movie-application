package com.haliltprkk.movieapplication.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haliltprkk.movieapplication.common.Resource
import com.haliltprkk.movieapplication.common.UiText
import com.haliltprkk.movieapplication.domain.models.Movie
import com.haliltprkk.movieapplication.domain.use_cases.home.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.*

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<HomeViewState>(HomeViewState.Init)
    fun getViewState(): StateFlow<HomeViewState> = _state.asStateFlow()

    init {
        getMovies(PAGE)
    }

    private fun setLoading(isLoading: Boolean) {
        _state.value = HomeViewState.IsLoading(isLoading)
    }

    private fun getMovies(page: Int) {
        getMoviesUseCase.invoke(page).onEach { result ->
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
        }.launchIn(viewModelScope)
    }

    sealed class HomeViewState {
        object Init : HomeViewState()
        data class IsLoading(val isLoading: Boolean) : HomeViewState()
        data class Success(val data: ArrayList<Movie>) : HomeViewState()
        object SuccessWithEmptyData : HomeViewState()
        data class Error(val error: UiText) : HomeViewState()
    }

    companion object {
        private const val PAGE = 1
    }
}
