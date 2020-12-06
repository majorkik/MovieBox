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
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.utils.FontSpan
import com.majorik.moviebox.feature.navigation.R
import com.majorik.moviebox.feature.navigation.databinding.DialogClearCacheBinding
import com.majorik.moviebox.R as AppResources

class ClearCacheDialog : DialogFragment(R.layout.dialog_clear_cache) {

    private val viewBinding: DialogClearCacheBinding by viewBinding()

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

        viewBinding.cacheSize.text = getString(R.string.navigation_cache_size_with_format, args.cacheSize ?: "")
        setSpannableCacheSizeText()

        viewBinding.btnCancel.setOnClickListener {
            dismiss()
        }

        viewBinding.btnClearNow.setSafeOnClickListener {
            context?.cacheDir?.deleteRecursively()
            (activity as? ClearDialogListener)?.onDialogDismiss()
            dismiss()
        }
    }

    private fun setSpannableCacheSizeText() {
        SpannableStringBuilder(viewBinding.cacheSize.text).apply {
            setSpan(
                FontSpan(
                    "cc_montserrat_semibold",
                    ResourcesCompat.getFont(
                        viewBinding.root.context,
                        AppResources.font.cc_montserrat_semibold
                    )
                ),
                viewBinding.cacheSize.text.length - args.cacheSize.length,
                viewBinding.cacheSize.text.length,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            )
            viewBinding.cacheSize.text = this
        }
    }
}
