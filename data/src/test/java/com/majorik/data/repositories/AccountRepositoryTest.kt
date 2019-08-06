package com.majorik.data.repositories

import com.majorik.data.base.BaseUnitTest
import com.majorik.data.di.appComponentTest
import com.majorik.domain.tmdbModels.request.RequestAddToWatchlist
import com.majorik.domain.tmdbModels.request.RequestMarkAsFavorite
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.test.inject
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class AccountRepositoryTest : BaseUnitTest() {
    private val accountRepository by inject<AccountRepository>()

    override fun isMockServerEnabled() = true

    override fun setUp() {
        super.setUp()
        startKoin {
            modules(appComponentTest(getMockUrl()))
        }
    }

    @Test
    fun getAccountDetails() {
        mockHttpResponse("accountRepository/accountDetails.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val accountDetails = accountRepository.getAccountDetails("fake")
            assertNotNull(accountDetails)
        }
    }

    @Test
    fun getFavoriteMovies() {
        mockHttpResponse("accountRepository/favoriteMovies.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val favoriteMovies = accountRepository.getFavoriteMovies("fake", "fake", "fake", 0)

            assertNotNull(favoriteMovies)
            assertNotNull(favoriteMovies?.results)
        }
    }

    @Test
    fun getFavoriteTVs() {
        mockHttpResponse("accountRepository/favoriteTVs.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val favoriteTVs = accountRepository.getFavoriteTVs("fake", "fake", "fake", 0)

            assertNotNull(favoriteTVs)
        }
    }

    @Test
    fun markIsFavorite() {
        mockHttpResponse("accountRepository/responseApi.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val responseApi =
                accountRepository.markIsFavorite(RequestMarkAsFavorite("fake", 0, true), "fake")

            assertNotNull(responseApi)
        }
    }

    @Test
    fun getRatedMovies() {
        mockHttpResponse("accountRepository/ratedMovies.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val ratedMovies = accountRepository.getRatedMovies("fake", "fake", "fake", 0)

            assertNotNull(ratedMovies)
            assertNotNull(ratedMovies?.results)
        }
    }

    @Test
    fun getRatedTVs() {
        mockHttpResponse("accountRepository/ratedTVs.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val ratedTVs = accountRepository.getRatedTVs("fake", "fake", "fake", 0)

            assertNotNull(ratedTVs)
            assertNotNull(ratedTVs?.results)
        }
    }

    @Test
    fun getRatedEpisodes() {
        mockHttpResponse("accountRepository/ratedEpisodes.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val ratedEpisodes = accountRepository.getRatedEpisodes("fake", "fake", "fake", 0)

            assertNotNull(ratedEpisodes)
            assertNotNull(ratedEpisodes?.results)
        }
    }

    @Test
    fun getWatchlistMovies() {
        mockHttpResponse("accountRepository/watchlistMovies.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val watchlistMovies = accountRepository.getWatchlistMovies("fake", "fake", "fake", 0)

            assertNotNull(watchlistMovies)
            assertNotNull(watchlistMovies?.results)
        }
    }

    @Test
    fun getWatchlistTVs() {
        mockHttpResponse("accountRepository/watchlistTVs.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val watchlistTVs = accountRepository.getWatchlistTVs("fake", "fake", "fake", 0)

            assertNotNull(watchlistTVs)
            assertNotNull(watchlistTVs?.results)
        }
    }

    @Test
    fun addToWatchlist() {
        mockHttpResponse("accountRepository/responseApi.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val responseApi =
                accountRepository.addToWatchlist(
                    RequestAddToWatchlist("fake", 0, true),
                    "fake"
                )

            assertNotNull(responseApi)
        }
    }


}
