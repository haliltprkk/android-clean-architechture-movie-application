package com.haliltprkk.movieapplication.presentation.movie_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haliltprkk.movieapplication.R
import com.haliltprkk.movieapplication.common.Resource
import com.haliltprkk.movieapplication.common.UiText
import com.haliltprkk.movieapplication.domain.models.Movie
import com.haliltprkk.movieapplication.domain.use_cases.movie_detail.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<MovieDetailViewState>(MovieDetailViewState.Init)
    fun getViewState(): StateFlow<MovieDetailViewState> = _state.asStateFlow()

    fun setLoading(isLoading: Boolean) {
        _state.value = MovieDetailViewState.Loading(isLoading)
    }

    fun getMovie(id: Long) {
        viewModelScope.launch {
            getMovieDetailUseCase.getMovieById(id).onEach {
                when (it) {
                    is Resource.Error -> {
                        setLoading(false)
                        _state.value = MovieDetailViewState.Error(it.message)
                    }
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        setLoading(false)
                        if (it.data == null) {
                            _state.value = MovieDetailViewState.Error(
                                UiText.StringResource(R.string.movieDetailPage_emptyError)
                            )
                        } else {
                            _state.value = MovieDetailViewState.Success(data = it.data)
                        }
                    }
                }
            }.collect()
        }
    }

    sealed class MovieDetailViewState {
        object Init : MovieDetailViewState()
        data class Loading(val isLoading: Boolean) : MovieDetailViewState()
        data class Success(val data: Movie) : MovieDetailViewState()
        data class Error(val error: UiText) : MovieDetailViewState()
    }
}
