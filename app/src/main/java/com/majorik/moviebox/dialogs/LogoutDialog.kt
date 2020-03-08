package com.majorik.moviebox.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.majorik.moviebox.R
import com.majorik.moviebox.extensions.setSafeOnClickListener
import com.majorik.moviebox.extensions.startActivityWithAnim
import com.majorik.moviebox.storage.CredentialsPrefsManager
import com.majorik.moviebox.ui.first_start.FirstStartActivity
import kotlinx.android.synthetic.main.dialog_logout.view.*
import org.koin.android.ext.android.inject

class LogoutDialog : DialogFragment() {

    private val credentialsManager: CredentialsPrefsManager by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_logout, container, false)
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
            context?.startActivityWithAnim(FirstStartActivity::class.java)
            dismiss()
        }
    }
}