package com.majorik.feature.navigation.presentation.main_page_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.majorik.moviebox.R
import com.majorik.feature.navigation.presentation.dialogs.LogoutDialog
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.setVisibilityOption
import com.majorik.library.base.extensions.startActivityWithAnim
import com.majorik.library.base.storage.CredentialsPrefsManager
import com.majorik.moviebox.ui.first_start.FirstStartActivity
import com.majorik.feature.navigation.presentation.settings.SettingsActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import org.koin.android.ext.android.inject

class ProfileFragment : Fragment() {

    private val credentialsManager: CredentialsPrefsManager by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(com.majorik.feature.navigation.R.layout.fragment_profile, container, false)

        return view
    }

    override fun onResume() {
        super.onResume()
        if (credentialsManager.getTmdbLoggedStatus()) {
            view?.layout_profile_buttons?.setVisibilityOption(true)
            view?.benefits_layout?.setVisibilityOption(false)
        } else {
            view?.benefits_layout?.setVisibilityOption(true)
            view?.layout_profile_buttons?.setVisibilityOption(false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListener()
    }

    private fun setClickListener() {
        btn_logout.setSafeOnClickListener {
            showLogoutDialog()
        }

        btn_sign_in.setSafeOnClickListener {
            credentialsManager.clearAll()
            context?.startActivityWithAnim(FirstStartActivity::class.java)
        }

        btn_settings.setSafeOnClickListener {
            context?.startActivityWithAnim(SettingsActivity::class.java)
        }
    }

    private fun showLogoutDialog() {
        val logoutDialog = LogoutDialog()
        logoutDialog.show(childFragmentManager, "logout_dialog")
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}
