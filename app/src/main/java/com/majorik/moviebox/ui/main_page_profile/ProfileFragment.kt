package com.majorik.moviebox.ui.main_page_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.majorik.moviebox.R
import com.majorik.moviebox.dialogs.LogoutDialog
import com.majorik.moviebox.extensions.setSafeOnClickListener
import com.majorik.moviebox.extensions.setVisibilityOption
import com.majorik.moviebox.extensions.startActivityWithAnim
import com.majorik.moviebox.storage.CredentialsPrefsManager
import com.majorik.moviebox.ui.first_start.FirstStartActivity
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
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

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
    }

    private fun showLogoutDialog() {
        val logoutDialog = LogoutDialog()
        logoutDialog.show(childFragmentManager, "logout_dialog")
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}
