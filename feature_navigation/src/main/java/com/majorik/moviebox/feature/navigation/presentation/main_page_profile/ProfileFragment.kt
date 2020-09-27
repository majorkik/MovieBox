package com.majorik.moviebox.feature.navigation.presentation.main_page_profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.constants.ScreenLinks
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.setVisibilityOption
import com.majorik.library.base.extensions.startActivityWithAnim
import com.majorik.library.base.storage.CredentialsPrefsManager
import com.majorik.moviebox.feature.navigation.R
import com.majorik.moviebox.feature.navigation.databinding.FragmentProfileBinding
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.account.AccountDetails
import com.majorik.moviebox.feature.navigation.presentation.adapters.profile.ProfileMoviesAdapter
import com.majorik.moviebox.feature.navigation.presentation.adapters.profile.ProfileTVsAdapter
import com.majorik.moviebox.feature.navigation.presentation.dialogs.LogoutDialog
import com.majorik.moviebox.feature.navigation.presentation.settings.SettingsActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewBinding: FragmentProfileBinding by viewBinding()

    private val viewModel: ProfileViewModel by viewModel()

    private val credentialsManager: CredentialsPrefsManager by inject()

    private val willWatchMoviesAdapter = ProfileMoviesAdapter()
    private val willWatchTVsAdapter = ProfileTVsAdapter()
    private val favoriteMoviesAdapter = ProfileMoviesAdapter()
    private val favoriteTVsAdapter = ProfileTVsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fetchData()
    }

    private fun fetchData() {
        viewModel.getAccountDetails()
        viewModel.fetchFavoriteMovies(AppConfig.REGION, 1)
        viewModel.fetchFavoriteTVs(AppConfig.REGION, 1)
        viewModel.fetchWatchlistMovies(AppConfig.REGION, 1)
        viewModel.fetchWatchlistTVs(AppConfig.REGION, 1)
    }

    override fun onResume() {
        super.onResume()
        if (credentialsManager.getTmdbLoggedStatus()) {
            viewBinding.profileLayout.setVisibilityOption(true)
            viewBinding.btnAuthTitle.text = "Выход"
        } else {
            viewBinding.profileLayout.setVisibilityOption(false)
            viewBinding.btnAuthTitle.text = "Вход в аккаунт"
            viewBinding.iconNameLetter.text = getString(R.string.navigation_profile_screen_guest)[0].toString()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapters()
        setObservers()
        setClickListener()
    }

    private fun setObservers() {
        viewModel.accountDetails.observe(
            viewLifecycleOwner,
            Observer {
                setAccountData(it)
            }
        )

        viewModel.favoriteMovies.observe(
            viewLifecycleOwner,
            Observer {
                favoriteMoviesAdapter.addItems(it)
                viewBinding.favoriteMoviesStub.setVisibilityOption(it.count() == 0)
            }
        )

        viewModel.favoriteTVs.observe(
            viewLifecycleOwner,
            Observer {
                favoriteTVsAdapter.addItems(it)
                viewBinding.favoriteTvsStub.setVisibilityOption(it.count() == 0)
            }
        )

        viewModel.watchlistMovies.observe(
            viewLifecycleOwner,
            Observer {
                willWatchMoviesAdapter.addItems(it)
                viewBinding.willWatchMoviesStub.setVisibilityOption(it.count() == 0)
            }
        )

        viewModel.watchlistTVs.observe(
            viewLifecycleOwner,
            Observer {
                willWatchTVsAdapter.addItems(it)
                viewBinding.willWatchTvsStub.setVisibilityOption(it.count() == 0)
            }
        )
    }

    private fun setAccountData(account: AccountDetails) {
        if (account.name.isBlank()) {
            viewBinding.iconNameLetter.text = account.username[0].toString()
            viewBinding.accountName.text = account.username
        } else {
            viewBinding.accountUsername.setVisibilityOption(true)
            viewBinding.iconNameLetter.text = account.name[0].toString()
            viewBinding.accountName.text = account.name
            viewBinding.accountUsername.text = account.username
        }
    }

    private fun setAdapters() {
        viewBinding.willWatchMoviesList.adapter = willWatchMoviesAdapter
        viewBinding.willWatchTvsList.adapter = willWatchTVsAdapter
        viewBinding.favoriteMoviesList.adapter = favoriteMoviesAdapter
        viewBinding.favoriteTvsList.adapter = favoriteTVsAdapter
    }

    private fun setClickListener() {
        viewBinding.btnAuth.setSafeOnClickListener {
            if (credentialsManager.getTmdbLoggedStatus()) {
                showLogoutDialog()
            } else {
                credentialsManager.clearAll()
                context?.startActivityWithAnim(ScreenLinks.authorization)
                activity?.finish()
            }
        }

        viewBinding.btnSettings.setSafeOnClickListener {
            context?.startActivityWithAnim(SettingsActivity::class.java)
        }
    }

    private fun showLogoutDialog() {
        val logoutDialog = LogoutDialog()
        logoutDialog.show(childFragmentManager, "logout_dialog")
    }
}
