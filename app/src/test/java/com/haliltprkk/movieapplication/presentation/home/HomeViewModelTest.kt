package com.haliltprkk.movieapplication.presentation.home

import com.google.common.truth.Truth.assertThat
import com.haliltprkk.movieapplication.common.utils.Resource
import com.haliltprkk.movieapplication.common.utils.UiText
import com.haliltprkk.movieapplication.domain.models.Movie
import com.haliltprkk.movieapplication.domain.usecases.home.GetPopularMoviesUseCase
import com.haliltprkk.movieapplication.utils.MockHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
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
class HomeViewModelTest {

    private val page: Int = 1
    private val errorMessage: String = "error"

    @Mock
    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = HomeViewModel(getPopularMoviesUseCase)
    }

    @Test
    fun `getPopularMoviesUseCase emits success`() = runTest {
        whenever(getPopularMoviesUseCase.getPopularMovies(any())).thenAnswer {
            flow { emit(Resource.Success(data = MockHelper.movieList)) }
        }
        viewModel.getMovies(page)
        val currentState = viewModel.getViewState()
        assertThat(currentState.value).isInstanceOf(HomeViewModel.HomeViewState.Success::class.java)
    }

    @Test
    fun `searchMovieUseCase emits success with empty list`() = runTest {
        whenever(getPopularMoviesUseCase.getPopularMovies(any())).thenAnswer {
            flow { emit(Resource.Success(data = arrayListOf<Movie>())) }
        }
        viewModel.getMovies(page)
        val currentState = viewModel.getViewState()
        assertThat(currentState.value).isInstanceOf(
            HomeViewModel.HomeViewState.SuccessWithEmptyData::class.java
        )
    }

    @Test
    fun `getPopularMoviesUseCase emits error`() = runTest {
        whenever(getPopularMoviesUseCase.getPopularMovies(any())).thenAnswer {
            flow<Resource<Any>> {
                emit(
                    Resource.Error(message = UiText.DynamicString(value = errorMessage))
                )
            }
        }
        viewModel.getMovies(page)
        val currentState = viewModel.getViewState()
        assertThat(currentState.value).isInstanceOf(HomeViewModel.HomeViewState.Error::class.java)
    }

    @Test
    fun `getPopularMoviesUseCase emits loading`() = runTest {
        whenever(getPopularMoviesUseCase.getPopularMovies(any())).thenAnswer {
            flow<Resource<Any>> { emit(Resource.Loading()) }
        }
        viewModel.getMovies(page)
        val currentState = viewModel.getViewState()
        assertThat(currentState.value).isInstanceOf(HomeViewModel.HomeViewState.Loading::class.java)
    }

    @Test
    fun `verify getPopularMoviesUseCase called with correct parameter`() = runTest {
        whenever(getPopularMoviesUseCase.getPopularMovies(any())).thenAnswer {
            flow<Resource<Any>> { emit(Resource.Loading()) }
        }
        viewModel.getMovies(page)
        verify(getPopularMoviesUseCase).getPopularMovies(eq(page))
    }

    @Test
    fun `verify setLoading function called with isLoading=true `() = runTest {
        viewModel.setLoading(true)
        val currentState = viewModel.getViewState()
        assertThat(currentState.value).isInstanceOf(HomeViewModel.HomeViewState.Loading::class.java)
        val loadingState = currentState.value as HomeViewModel.HomeViewState.Loading
        assertThat(loadingState.isLoading).isEqualTo(true)
    }

    @Test
    fun `verify setLoading function called with isLoading=false `() = runTest {
        viewModel.setLoading(false)
        val currentState = viewModel.getViewState()
        assertThat(currentState.value).isInstanceOf(HomeViewModel.HomeViewState.Loading::class.java)
        val loadingState = currentState.value as HomeViewModel.HomeViewState.Loading
        assertThat(loadingState.isLoading).isEqualTo(false)
    }

    @After
    fun tearDown() = Dispatchers.resetMain()
}
