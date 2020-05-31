package com.majorik.moviebox.feature.navigation.presentation.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.majorik.library.base.constants.ScreenLinks
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.startActivityWithAnim
import com.majorik.library.base.storage.CredentialsPrefsManager
import com.majorik.library.base.utils.PACKAGE_NAME
import kotlinx.android.synthetic.main.dialog_logout.view.*
import org.koin.android.ext.android.inject

class LogoutDialog : DialogFragment() {

    private val credentialsManager: CredentialsPrefsManager by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.majorik.moviebox.feature.navigation.R.layout.dialog_logout, container, false)
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.btn_cancel.setOnClickListener {
            dismiss()
        }

        view.btn_logout_confirm.setSafeOnClickListener {
            credentialsManager.clearAll()
            context?.startActivityWithAnim(ScreenLinks.authorization)
            activity?.finish()
            dismiss()
        }
    }
}
