package com.majorik.moviebox.feature.details.presentation.watch_online

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.moviebox.R as AppRes
import com.majorik.moviebox.feature.details.R
import kotlinx.android.synthetic.main.dialog_watch_online.*

class WatchOnlineDialog : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NORMAL, AppRes.style.Widget_AppTheme_BottomSheet)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_watch_online, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListener()
    }

    private fun setClickListener() {
        btn_close.setSafeOnClickListener {
            dismiss()
        }
    }
}
