package com.majorik.moviebox.feature.navigation.presentation.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majorik.library.base.constants.ScreenLinks
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.startActivityWithAnim
import com.majorik.library.base.storage.CredentialsPrefsManager
import com.majorik.moviebox.feature.navigation.R
import com.majorik.moviebox.feature.navigation.databinding.DialogLogoutBinding
import org.koin.android.ext.android.inject

class LogoutDialog : DialogFragment(R.layout.dialog_logout) {

    private val viewBinding: DialogLogoutBinding by viewBinding()

    private val credentialsManager: CredentialsPrefsManager by inject()

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

        viewBinding.btnCancel.setOnClickListener {
            dismiss()
        }

        viewBinding.btnLogoutConfirm.setSafeOnClickListener {
            credentialsManager.clearAll()
            context?.startActivityWithAnim(ScreenLinks.authorization)
            activity?.finish()
            dismiss()
        }
    }
}
