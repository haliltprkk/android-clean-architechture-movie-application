package com.haliltprkk.movieapplication.presentation.movie_detail

import com.google.common.truth.Truth
import com.haliltprkk.movieapplication.common.Resource
import com.haliltprkk.movieapplication.common.UiText
import com.haliltprkk.movieapplication.domain.use_cases.movie_detail.GetMovieDetailUseCase
import com.haliltprkk.movieapplication.utils.MockHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {

    private val movieId: Long = 1
    private val errorMessage: String = "error"

    @Mock
    private lateinit var getMovieDetailUseCase: GetMovieDetailUseCase

    private lateinit var viewModel: MovieDetailViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = MovieDetailViewModel(getMovieDetailUseCase)
    }

    @Test
    fun `getMovieDetailUseCase emits success`() = runTest {
        whenever(getMovieDetailUseCase.getMovieById(any())).thenAnswer {
            flow { emit(Resource.Success(data = MockHelper.movie)) }
        }
        viewModel.getMovie(movieId)
        val currentState = viewModel.getViewState()
        Truth.assertThat(currentState.value).isInstanceOf(MovieDetailViewModel.MovieDetailViewState.Success::class.java)
    }

    @Test
    fun `getMovieDetailUseCase emits error`() = runTest {
        whenever(getMovieDetailUseCase.getMovieById(any())).thenAnswer {
            flow<Resource<Any>> { emit(Resource.Error(message = UiText.DynamicString(value = errorMessage))) }
        }
        viewModel.getMovie(movieId)
        val currentState = viewModel.getViewState()
        Truth.assertThat(currentState.value).isInstanceOf(MovieDetailViewModel.MovieDetailViewState.Error::class.java)
    }

    @Test
    fun `getMovieDetailUseCase emits loading`() = runTest {
        whenever(getMovieDetailUseCase.getMovieById(any())).thenAnswer {
            flow<Resource<Any>> { emit(Resource.Loading()) }
        }
        viewModel.getMovie(movieId)
        val currentState = viewModel.getViewState()
        Truth.assertThat(currentState.value).isInstanceOf(MovieDetailViewModel.MovieDetailViewState.Loading::class.java)
    }

    @Test
    fun `verify getMovieDetailUseCase called with correct parameter`() = runTest {
        whenever(getMovieDetailUseCase.getMovieById(any())).thenAnswer {
            flow<Resource<Any>> { emit(Resource.Loading()) }
        }
        viewModel.getMovie(movieId)
        verify(getMovieDetailUseCase).getMovieById(eq(movieId))
    }

    @Test
    fun `verify setLoading function called with isLoading=true `() = runTest {
        viewModel.setLoading(true)
        val currentState = viewModel.getViewState()
        Truth.assertThat(currentState.value).isInstanceOf(MovieDetailViewModel.MovieDetailViewState.Loading::class.java)
        val loadingState = currentState.value as MovieDetailViewModel.MovieDetailViewState.Loading
        Truth.assertThat(loadingState.isLoading).isEqualTo(true)
    }

    @Test
    fun `verify setLoading function called with isLoading=false `() = runTest {
        viewModel.setLoading(false)
        val currentState = viewModel.getViewState()
        Truth.assertThat(currentState.value).isInstanceOf(MovieDetailViewModel.MovieDetailViewState.Loading::class.java)
        val loadingState = currentState.value as MovieDetailViewModel.MovieDetailViewState.Loading
        Truth.assertThat(loadingState.isLoading).isEqualTo(false)
    }

    @After
    fun tearDown() = Dispatchers.resetMain()
}
