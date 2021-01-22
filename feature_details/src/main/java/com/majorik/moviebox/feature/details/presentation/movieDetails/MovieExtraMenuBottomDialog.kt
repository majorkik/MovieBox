package com.majorik.moviebox.feature.details.presentation.movieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.moviebox.feature.details.R
import com.majorik.moviebox.feature.details.databinding.DialogMovieExtraMenuBinding
import com.majorik.moviebox.R as AppRes

class MovieExtraMenuBottomDialog : BottomSheetDialogFragment() {

    private val viewBinding: DialogMovieExtraMenuBinding by viewBinding()


    companion object {
        const val KEY_EXTRAS_SELECTED = "key_extras_selected"

        const val CODE_RECOMMENDATIONS = 50
        const val CODE_SIMILAR = 50
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NORMAL, AppRes.style.Widget_AppTheme_BottomSheet)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_movie_extra_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListener()
    }

    private fun setClickListener() {
        viewBinding.btnClose.setSafeOnClickListener {
            dismiss()
        }

        viewBinding.btnRecommendations.setSafeOnClickListener {
            dismiss()
            setFragmentResult(KEY_EXTRAS_SELECTED, bundleOf("code" to CODE_RECOMMENDATIONS))
        }
    }
}
