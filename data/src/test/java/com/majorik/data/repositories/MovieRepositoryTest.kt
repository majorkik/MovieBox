package com.majorik.data.repositories

import com.majorik.data.base.BaseUnitTest
import com.majorik.data.di.appComponentTest
import java.net.HttpURLConnection
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.test.inject

@RunWith(JUnit4::class)
class MovieRepositoryTest : BaseUnitTest() {
    private val movieRepository by inject<MovieRepository>()

    override fun isMockServerEnabled() = true

    override fun setUp() {
        super.setUp()
        startKoin {
            modules(appComponentTest(getMockUrl()))
        }
    }

    @Test
    fun getMovieById() {
        mockHttpResponse("movieRepository/movie.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val movie = movieRepository.getMovieById(0, "", "", "")
            assertNotNull(movie)
        }
    }

    @Test
    fun getPopularMovies() {
        mockHttpResponse("movieRepository/popularMovies.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val popularMovies = movieRepository.getPopularMovies("", 0, "")
            assertNotNull(popularMovies)
        }
    }

    @Test
    fun getTopRatedMovies() {
        mockHttpResponse("movieRepository/topRatedMovies.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val topRatedMovies = movieRepository.getTopRatedMovies("", 0, "")
            assertNotNull(topRatedMovies)
        }
    }

    @Test
    fun getMovieGenres() {
        mockHttpResponse("movieRepository/movieGenres.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val movieGenres = movieRepository.getMovieGenres("")
            assertNotNull(movieGenres)
        }
    }

    @Test
    fun getAccountStatesForMovie() {
        mockHttpResponse("movieRepository/accountStatesMovies.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val accountStates = movieRepository.getAccountStatesForMovie(0, "", "")
            assertNotNull(accountStates)
        }
    }
}
