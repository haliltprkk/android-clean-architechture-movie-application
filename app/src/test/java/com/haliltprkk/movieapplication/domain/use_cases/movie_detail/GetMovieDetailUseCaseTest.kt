package com.haliltprkk.movieapplication.domain.use_cases.movie_detail

import com.google.common.truth.Truth
import com.haliltprkk.movieapplication.common.Resource
import com.haliltprkk.movieapplication.common.UiText
import com.haliltprkk.movieapplication.domain.repositories.MovieRepository
import com.haliltprkk.movieapplication.fake_db.MockHelper
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetMovieDetailUseCaseTest {
    private lateinit var getMovieDetailUseCase: GetMovieDetailUseCase

    @Mock
    private lateinit var movieRepository: MovieRepository
    private val movieId: Long = 0

    @Before
    fun setUp() {
        getMovieDetailUseCase = GetMovieDetailUseCase(movieRepository)
    }

    @Test
    fun `check getMovie() success case`() = runBlocking {
        // when
        whenever(movieRepository.getMovie(movieId)).thenAnswer { MockHelper.movieDto }
        val result = getMovieDetailUseCase(movieId)
        val flowList = result.toList()
        // then
        Truth.assertThat(flowList[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(flowList[1]).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `check getMovie() http exception error case`() = runBlocking {
        // when
        whenever(movieRepository.getMovie(movieId)).thenAnswer { throw MockHelper.httpException }
        val result = getMovieDetailUseCase(movieId)
        val flowList = result.toList()
        // then
        Truth.assertThat(flowList[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(flowList[1]).isInstanceOf(Resource.Error::class.java)
        Truth.assertThat(flowList[1].message).isInstanceOf(UiText.DynamicString::class.java)
    }

    @Test
    fun `check getMovie() io exception error case`() = runBlocking {
        // when
        whenever(movieRepository.getMovie(movieId)).thenAnswer { throw MockHelper.ioException }
        val result = getMovieDetailUseCase(movieId)
        val flowList = result.toList()
        // then

        Truth.assertThat(flowList[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(flowList[1]).isInstanceOf(Resource.Error::class.java)
        Truth.assertThat(flowList[1].message).isInstanceOf(UiText.StringResource::class.java)
    }
}