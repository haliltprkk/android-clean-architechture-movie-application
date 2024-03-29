package com.haliltprkk.movieapplication.domain.usecases.search

import com.google.common.truth.Truth.assertThat
import com.haliltprkk.movieapplication.common.utils.Resource
import com.haliltprkk.movieapplication.common.utils.UiText
import com.haliltprkk.movieapplication.domain.mappers.MovieMapper
import com.haliltprkk.movieapplication.domain.repositories.MovieRepository
import com.haliltprkk.movieapplication.utils.MockHelper
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class SearchMovieUseCaseTest {
    private lateinit var searchMovieUseCase: SearchMovieUseCase

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var mapper: MovieMapper
    private val query: String = "query"

    @Before
    fun setUp() {
        searchMovieUseCase = SearchMovieUseCaseImpl(movieRepository, mapper)
    }

    @Test
    fun `check searchMovie() success case`() = runBlocking {
        // when
        whenever(movieRepository.searchMovie(query)).thenAnswer { MockHelper.searchMovieDt }
        val result = searchMovieUseCase.searchMovie(query)
        val flowList = result.toList()
        // then
        assertThat(flowList[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(flowList[1]).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `check searchMovie() http exception error case`() = runBlocking {
        // when
        whenever(movieRepository.searchMovie(query)).thenAnswer { throw MockHelper.getHttpException() }
        val result = searchMovieUseCase.searchMovie(query)
        val flowList = result.toList()
        // then
        assertThat(flowList[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(flowList[1]).isInstanceOf(Resource.Error::class.java)
        assertThat(flowList[1].message).isInstanceOf(UiText.DynamicString::class.java)
    }

    @Test
    fun `check searchMovie() io exception error case`() = runBlocking {
        // when
        whenever(movieRepository.searchMovie(query)).thenAnswer { throw MockHelper.ioException }
        val result = searchMovieUseCase.searchMovie(query)
        val flowList = result.toList()
        // then
        assertThat(flowList[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(flowList[1]).isInstanceOf(Resource.Error::class.java)
        assertThat(flowList[1].message).isInstanceOf(UiText.StringResource::class.java)
    }
}
