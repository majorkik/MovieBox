package com.majorik.moviebox.feature.collections.presentation.genres

import android.os.Bundle
import android.view.View
import androidx.core.view.updatePadding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majorik.moviebox.feature.collections.presentation.adapters.GenresInlineAdapter
import com.majorik.library.base.base.BaseSlidingActivity
import com.majorik.library.base.enums.GenresType
import com.majorik.library.base.enums.SELECTED_GENRES_TYPE
import com.majorik.library.base.extensions.px
import com.majorik.moviebox.feature.collections.R
import com.majorik.library.base.extensions.setWindowTransparency
import com.majorik.library.base.extensions.updateMargin
import com.majorik.moviebox.feature.collections.databinding.ActivityGenresBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenresActivity : BaseSlidingActivity() {

    private val viewBinding: ActivityGenresBinding by viewBinding(R.id.genres_container)

    private val genresViewModel: GenresViewModel by viewModel()

    override fun getRootView(): View = window.decorView.rootView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genres)

        setWindowTransparency(::updateMargins)

        val selectedGenresType =
            intent.getIntExtra(SELECTED_GENRES_TYPE, GenresType.MOVIE_GENRES.ordinal)

        genresViewModel.fetchGenres(selectedGenresType)

        setObservers()
    }

    private fun updateMargins(
        statusBarSize: Int,
        @Suppress("UNUSED_PARAMETER") navigationBarSize: Int
    ) {
        viewBinding.genresToolbar.updateMargin(top = statusBarSize)
        viewBinding.listGenres.updatePadding(bottom = navigationBarSize + 16.px())
    }

    override fun onSlidingStarted() {}

    override fun onSlidingFinished() {}

    override fun canSlideDown(): Boolean = true

    private fun setObservers() {
        genresViewModel.genresLiveData.observe(
            this,
            {
                viewBinding.listGenres.adapter = GenresInlineAdapter(it.genres)
            }
        )
    }
}
