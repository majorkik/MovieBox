package com.majorik.moviebox.feature.collections.presentation.genres

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.majorik.moviebox.feature.collections.presentation.adapters.GenresInlineAdapter
import com.majorik.library.base.base.BaseSlidingActivity
import com.majorik.library.base.enums.GenresType
import com.majorik.library.base.enums.SELECTED_GENRES_TYPE
import com.majorik.moviebox.feature.collections.R
import com.majorik.library.base.extensions.setWindowTransparency
import com.majorik.library.base.extensions.updateMargin
import kotlinx.android.synthetic.main.activity_genres.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenresActivity : BaseSlidingActivity() {

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
        genres_toolbar.updateMargin(top = statusBarSize)
        nested_scroll_view.updateMargin(bottom = navigationBarSize)
    }

    override fun onSlidingStarted() {}

    override fun onSlidingFinished() {}

    override fun canSlideDown(): Boolean = true

    private fun setObservers() {
        genresViewModel.genresLiveData.observe(this, {
            list_genres.adapter = GenresInlineAdapter(it.genres)
        })
    }
}
