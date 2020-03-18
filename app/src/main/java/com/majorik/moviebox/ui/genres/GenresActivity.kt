package com.majorik.moviebox.ui.genres

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.GenresInlineAdapter
import com.majorik.moviebox.extensions.setWindowTransparency
import com.majorik.moviebox.extensions.updateMargin
import com.majorik.moviebox.ui.base.BaseSlidingActivity
import kotlinx.android.synthetic.main.activity_genres.*
import org.koin.android.viewmodel.ext.android.viewModel

class GenresActivity : BaseSlidingActivity() {

    private val genresViewModel: GenresViewModel by viewModel()

    enum class GenresType {
        MOVIE_GENRES, TV_GENRES
    }

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

    private fun updateMargins(statusBarSize: Int, @Suppress("UNUSED_PARAMETER") navigationBarSize: Int) {
        genres_toolbar.updateMargin(top = statusBarSize)
        nested_scroll_view.updateMargin(bottom = navigationBarSize)
    }

    override fun onSlidingStarted() {}

    override fun onSlidingFinished() {}

    override fun canSlideDown(): Boolean = true

    private fun setObservers() {
        genresViewModel.genresLiveData.observe(this, Observer {
            list_genres.adapter = GenresInlineAdapter(it)
        })
    }

    companion object {
        const val SELECTED_GENRES_TYPE = "selected_genres_type"
    }
}
