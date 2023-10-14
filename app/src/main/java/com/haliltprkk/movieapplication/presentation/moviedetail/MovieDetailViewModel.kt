package com.haliltprkk.movieapplication.presentation.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haliltprkk.movieapplication.R
import com.haliltprkk.movieapplication.common.utils.Resource
import com.haliltprkk.movieapplication.common.utils.UiText
import com.haliltprkk.movieapplication.domain.models.Movie
import com.haliltprkk.movieapplication.domain.usecases.moviedetail.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase
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
            }.launchIn(this)
        }
    }

    sealed class MovieDetailViewState {
        object Init : MovieDetailViewState()
        data class Loading(val isLoading: Boolean) : MovieDetailViewState()
        data class Success(val data: Movie) : MovieDetailViewState()
        data class Error(val error: UiText) : MovieDetailViewState()
    }
}
