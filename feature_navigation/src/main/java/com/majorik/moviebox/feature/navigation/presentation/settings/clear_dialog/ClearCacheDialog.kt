package com.majorik.moviebox.feature.navigation.presentation.settings.clear_dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.utils.FontSpan
import com.majorik.moviebox.feature.navigation.R
import com.majorik.moviebox.R as AppResources
import kotlinx.android.synthetic.main.dialog_clear_cache.*

class ClearCacheDialog : DialogFragment(R.layout.dialog_clear_cache) {

    private val args: ClearCacheDialogArgs by navArgs()

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

        cache_size.text = getString(R.string.navigation_cache_size_with_format, args.cacheSize ?: "")
        setSpannableCacheSizeText()

        btn_cancel.setOnClickListener {
            dismiss()
        }

        btn_clear_now.setSafeOnClickListener {
            context?.cacheDir?.deleteRecursively()
            (activity as? ClearDialogListener)?.onDialogDismiss()
            dismiss()
        }
    }

    private fun setSpannableCacheSizeText() {
        context?.run {
            SpannableStringBuilder(cache_size.text).apply {
                setSpan(
                    FontSpan(
                        "cc_montserrat_semibold",
                        ResourcesCompat.getFont(
                            this@run,
                            AppResources.font.cc_montserrat_semibold
                        )
                    ),
                    cache_size.text.length - args.cacheSize.length,
                    cache_size.text.length,
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                )
                cache_size.text = this
            }
        }
    }
}
