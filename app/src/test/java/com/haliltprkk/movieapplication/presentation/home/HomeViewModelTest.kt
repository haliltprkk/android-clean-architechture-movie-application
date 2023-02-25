package com.haliltprkk.movieapplication.presentation.home

import com.google.common.truth.Truth.assertThat
import com.haliltprkk.movieapplication.common.Resource
import com.haliltprkk.movieapplication.domain.use_cases.home.GetPopularMoviesUseCase
import com.haliltprkk.movieapplication.utils.MockHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    private val page: Int = 1

    @Mock
    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = HomeViewModel(getPopularMoviesUseCase)
    }

    @Test
    fun `getMovies() called with getPopularMoviesUseCase success`() = runTest {
        whenever(getPopularMoviesUseCase.getPopularMovies(page)).thenAnswer {
            flow { emit(Resource.Success(data = MockHelper.movieList)) }
        }
        viewModel.getMovies(page)
        val currentState = viewModel.getViewState()
        assertThat(currentState.value).isInstanceOf(HomeViewModel.HomeViewState.Success::class.java)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
