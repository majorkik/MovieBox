package com.majorik.moviebox.feature.navigation.presentation.main_page_profile

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majorik.library.base.constants.ScreenLinks
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.setVisibilityOption
import com.majorik.library.base.extensions.startActivityWithAnim
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.moviebox.feature.navigation.R
import com.majorik.moviebox.feature.navigation.databinding.FragmentProfileBinding
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.account.AccountDetails
import com.majorik.moviebox.feature.navigation.presentation.dialogs.LogoutDialog
import com.majorik.moviebox.feature.navigation.presentation.main_page_profile.adapters.ProfileMoviesAdapter
import com.majorik.moviebox.feature.navigation.presentation.main_page_profile.adapters.ProfileTVsAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewBinding: FragmentProfileBinding by viewBinding()

    private val viewModel: ProfileViewModel by viewModel()

    private val willWatchMoviesAdapter = ProfileMoviesAdapter(::openMovieDetails)
    private val willWatchTVsAdapter = ProfileTVsAdapter(::openTVDetails)
    private val favoriteMoviesAdapter = ProfileMoviesAdapter(::openMovieDetails)
    private val favoriteTVsAdapter = ProfileTVsAdapter(::openTVDetails)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fetchData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapters()

        lifecycleScope.launchWhenStarted {

            launch {
                favoriteMoviesAdapter.loadStateFlow.collect { loadState ->
                    viewBinding.favoriteMoviesStub.isVisible =
                        loadState.source.refresh is LoadState.NotLoading && favoriteMoviesAdapter.itemCount < 1
                }
            }

            launch {
                favoriteTVsAdapter.loadStateFlow.collect { loadState ->
                    viewBinding.favoriteTvsStub.isVisible =
                        loadState.source.refresh is LoadState.NotLoading && favoriteTVsAdapter.itemCount < 1
                }
            }

            launch {
                willWatchMoviesAdapter.loadStateFlow.collect { loadState ->
                    viewBinding.willWatchMoviesStub.isVisible =
                        loadState.source.refresh is LoadState.NotLoading && willWatchMoviesAdapter.itemCount < 1
                }
            }

            launch {
                willWatchTVsAdapter.loadStateFlow.collect { loadState ->
                    viewBinding.willWatchTvsStub.isVisible =
                        loadState.source.refresh is LoadState.NotLoading && willWatchTVsAdapter.itemCount < 1
                }
            }
        }

        setClickListener()
        setObservers()
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.credentialsManager.getTmdbLoggedStatus()) {
            viewBinding.profileLayout.setVisibilityOption(true)
            viewBinding.btnAuthTitle.text = getString(R.string.profile_screen_auth_button_logout)
        } else {
            viewBinding.profileLayout.setVisibilityOption(false)
            viewBinding.btnAuthTitle.text = getString(R.string.profile_screen_auth_button_log_in)
            viewBinding.iconNameLetter.text = getString(R.string.navigation_profile_screen_guest)[0].toString()
        }
    }

    private fun fetchData() {
        viewModel.getAccountDetails()
    }

    private fun setObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.accountDetails.collectLatest { result ->
                result?.let { it -> setAccountDetailsResult(it) }
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.favoriteMoviesFlow.collectLatest { pagingData ->
                favoriteMoviesAdapter.submitData(pagingData)
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.watchlistMoviesFlow.collectLatest { pagingData ->
                willWatchMoviesAdapter.submitData(pagingData)
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.favoriteTVsFlow.collectLatest { pagingData ->
                favoriteTVsAdapter.submitData(pagingData)
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.watchlistTVsFlow.collectLatest { pagingData ->
                willWatchTVsAdapter.submitData(pagingData)
            }
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
            if (viewModel.credentialsManager.getTmdbLoggedStatus()) {
                showLogoutDialog()
            } else {
                viewModel.credentialsManager.clearAll()
                context?.startActivityWithAnim(ScreenLinks.authorization)
                activity?.finish()
            }
        }

        viewBinding.btnSettings.setSafeOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionToSettings())
        }
    }

    private fun showLogoutDialog() {
        val logoutDialog = LogoutDialog()
        logoutDialog.show(childFragmentManager, "logout_dialog")
    }

    /**
     * Actions
     */

    private fun openMovieDetails(id: Int) {
        findNavController().navigate(ProfileFragmentDirections.actionToMovieDetails(id))
    }

    private fun openTVDetails(id: Int) {
        findNavController().navigate(ProfileFragmentDirections.actionToTvDetails(id))
    }

    /**
     * Results
     */

    private fun setAccountDetailsResult(result: ResultWrapper<AccountDetails>) {
        when (result) {
            is ResultWrapper.Success -> {
                setAccountDetails(result.value)
            }
        }
    }

    /**
     * Details
     */

    private fun setAccountDetails(account: AccountDetails) {
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
}
