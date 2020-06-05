package com.majorik.moviebox.feature.navigation.presentation.main_page_profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majorik.library.base.constants.ScreenLinks
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.setVisibilityOption
import com.majorik.library.base.extensions.startActivityWithAnim
import com.majorik.library.base.storage.CredentialsPrefsManager
import com.majorik.moviebox.feature.navigation.R
import com.majorik.moviebox.feature.navigation.databinding.FragmentProfileBinding
import com.majorik.moviebox.feature.navigation.presentation.dialogs.LogoutDialog
import com.majorik.moviebox.feature.navigation.presentation.settings.SettingsActivity
import org.koin.android.ext.android.inject

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewBinding: FragmentProfileBinding by viewBinding()

    private val credentialsManager: CredentialsPrefsManager by inject()

    override fun onResume() {
        super.onResume()
        if (credentialsManager.getTmdbLoggedStatus()) {
            viewBinding.layoutProfileButtons.setVisibilityOption(true)
            viewBinding.benefitsLayout.setVisibilityOption(false)
        } else {
            viewBinding.benefitsLayout.setVisibilityOption(true)
            viewBinding.layoutProfileButtons.setVisibilityOption(false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListener()
    }

    private fun setClickListener() {
        viewBinding.btnLogout.setSafeOnClickListener {
            showLogoutDialog()
        }

        viewBinding.btnSignIn.setSafeOnClickListener {
            credentialsManager.clearAll()
            context?.startActivityWithAnim(ScreenLinks.authorization)
            activity?.finish()
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
