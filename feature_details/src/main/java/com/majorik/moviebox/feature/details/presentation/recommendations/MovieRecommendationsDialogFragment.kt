package com.majorik.moviebox.feature.details.presentation.recommendations

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.majorik.moviebox.R as AppRes

class MovieRecommendationsDialogFragment : DialogFragment() {

    private val viewModel: MovieRecommendationsViewModel by viewModel()

    private val args: MovieRecommendationsDialogFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, AppRes.style.AppTheme_DialogFragmentTransparentStyle)
        viewModel.movieId = args.movieId
    }
}
