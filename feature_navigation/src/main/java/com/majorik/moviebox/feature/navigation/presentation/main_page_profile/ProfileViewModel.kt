package com.majorik.moviebox.feature.navigation.presentation.main_page_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.library.base.storage.CredentialsPrefsManager
import com.majorik.moviebox.feature.navigation.data.repositories.AccountRepository
import com.majorik.moviebox.feature.navigation.domain.enums.ProfileMoviesType
import com.majorik.moviebox.feature.navigation.domain.enums.ProfileTVsType
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.account.AccountDetails
import com.majorik.moviebox.feature.navigation.presentation.main_page_profile.datasources.ProfileMoviesPagingDataSource
import com.majorik.moviebox.feature.navigation.presentation.main_page_profile.datasources.ProfileTVsPagingDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val accountRepository: AccountRepository,
    val credentialsManager: CredentialsPrefsManager
) : ViewModel() {

    /**
     * Account details
     */

    val accountDetails = MutableStateFlow<ResultWrapper<AccountDetails>?>(null)

    fun getAccountDetails() {
        viewModelScope.launch {
            val sessionId = credentialsManager.getTmdbSessionID() ?: ""
            val response = accountRepository.getAccountDetails(sessionId)

            accountDetails.value = response
        }
    }

    /**
     * Favorite movies
     */

    var favoriteMoviesDataSource: ProfileMoviesPagingDataSource? = null

    var favoriteMoviesFlow = Pager(PagingConfig(pageSize = 20)) {
        ProfileMoviesPagingDataSource(
            credentialsManager.getTmdbSessionID() ?: "",
            accountRepository,
            ProfileMoviesType.FAVORITE_MOVIES,
            AppConfig.REGION
        ).also {
            favoriteMoviesDataSource = it
        }
    }.flow.cachedIn(viewModelScope)

    /**
     * Watchlist movies
     */

    var watchlistMoviesDataSource: ProfileMoviesPagingDataSource? = null

    var watchlistMoviesFlow = Pager(PagingConfig(pageSize = 20)) {
        ProfileMoviesPagingDataSource(
            credentialsManager.getTmdbSessionID() ?: "",
            accountRepository,
            ProfileMoviesType.WATCHLIST_MOVIES,
            AppConfig.REGION
        ).also {
            watchlistMoviesDataSource = it
        }
    }.flow.cachedIn(viewModelScope)

    /**
     * Favorite tv shows
     */

    var favoriteTVsDataSource: ProfileTVsPagingDataSource? = null

    var favoriteTVsFlow = Pager(PagingConfig(pageSize = 20)) {
        ProfileTVsPagingDataSource(
            credentialsManager.getTmdbSessionID() ?: "",
            accountRepository,
            ProfileTVsType.FAVORITE_TVS,
            AppConfig.REGION
        ).also {
            favoriteTVsDataSource = it
        }
    }.flow.cachedIn(viewModelScope)

    /**
     * Watchlist tv shows
     */

    var watchlistTVsDataSource: ProfileTVsPagingDataSource? = null

    var watchlistTVsFlow = Pager(PagingConfig(pageSize = 20)) {
        ProfileTVsPagingDataSource(
            credentialsManager.getTmdbSessionID() ?: "",
            accountRepository,
            ProfileTVsType.WATCHLIST_TVS,
            AppConfig.REGION
        ).also {
            watchlistTVsDataSource = it
        }
    }.flow.cachedIn(viewModelScope)
}
