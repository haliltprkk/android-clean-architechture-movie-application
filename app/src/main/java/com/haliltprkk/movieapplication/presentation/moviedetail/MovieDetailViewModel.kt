package com.haliltprkk.movieapplication.presentation.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haliltprkk.movieapplication.R
import com.haliltprkk.movieapplication.common.utils.Resource
import com.haliltprkk.movieapplication.common.utils.UiText
import com.haliltprkk.movieapplication.domain.models.Cast
import com.haliltprkk.movieapplication.domain.models.Movie
import com.haliltprkk.movieapplication.domain.usecases.moviedetail.GetMovieCastsUseCase
import com.haliltprkk.movieapplication.domain.usecases.moviedetail.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieCastsUseCase: GetMovieCastsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<MovieDetailViewState>(MovieDetailViewState.Init)
    fun getViewState(): StateFlow<MovieDetailViewState> = _state.asStateFlow()

    fun getMovieDetails(id: Long) {
        viewModelScope.launch {
            getMovieDetailUseCase.getMovieById(id).combine(getMovieCastsUseCase.getCasts(id)) { movie, casts ->
                if (movie is Resource.Loading || casts is Resource.Loading) {
                    _state.value = MovieDetailViewState.Loading
                } else if (movie is Resource.Error) {
                    _state.value = MovieDetailViewState.Error(
                        UiText.StringResource(R.string.movieDetailPage_emptyError)
                    )
                } else if (movie is Resource.Success) {
                    if (movie.data == null) {
                        _state.value = MovieDetailViewState.Error(
                            UiText.StringResource(R.string.movieDetailPage_emptyError)
                        )
                    } else {
                        _state.value =
                            MovieDetailViewState.Success(
                                movie = movie.data,
                                castList = casts.data.orEmpty()
                            )
                    }
                }
            }.launchIn(this)
        }
    }

    sealed class MovieDetailViewState {
        object Init : MovieDetailViewState()
        object Loading : MovieDetailViewState()
        data class Success(val movie: Movie, val castList: List<Cast>) : MovieDetailViewState()
        data class Error(val error: UiText) : MovieDetailViewState()
    }
}
