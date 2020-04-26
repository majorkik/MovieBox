package com.majorik.moviebox.feature.navigation.presentation.settings.clear_dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.utils.FontSpan
import com.majorik.moviebox.feature.navigation.R
import kotlinx.android.synthetic.main.dialog_clear_cache.*

class ClearCacheDialog : DialogFragment() {

    private var cacheSize: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cacheSize = arguments?.getString(CACHE_SIZE_ARG) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.majorik.moviebox.feature.navigation.R.layout.dialog_clear_cache, container, false)
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

        cache_size.text = getString(com.majorik.moviebox.R.string.cache_size_with_format, cacheSize)
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
                            com.majorik.moviebox.R.font.cc_montserrat_semibold
                        )
                    ),
                    cache_size.text.length - cacheSize.length,
                    cache_size.text.length,
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                )
                cache_size.text = this
            }
        }
    }

    companion object {
        const val CACHE_SIZE_ARG = "cache_size"

        fun newInstance(cacheSizeFormattedToString: String?) =
            ClearCacheDialog().apply {
                arguments = Bundle().apply {
                    putString(CACHE_SIZE_ARG, cacheSizeFormattedToString)
                }
            }
    }
}
