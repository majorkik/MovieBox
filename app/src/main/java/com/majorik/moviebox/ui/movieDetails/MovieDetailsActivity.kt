package com.majorik.moviebox.ui.movieDetails

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.account.AccountStates
import com.majorik.domain.tmdbModels.video.Videos
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.ImageSliderAdapter
import com.majorik.moviebox.adapters.PersonAdapter
import com.majorik.moviebox.extensions.*
import com.majorik.moviebox.ui.base.BaseSlidingActivity
import com.majorik.moviebox.utils.SharedPrefsManager
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.layout_movie_details.*
import kotlinx.android.synthetic.main.layout_movie_details.btn_watch_trailer
import kotlinx.android.synthetic.main.layout_movie_details.toggle_favorite
import kotlinx.android.synthetic.main.layout_movie_details.toggle_watchlist
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.DecimalFormat

class MovieDetailsActivity : BaseSlidingActivity() {
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModel()
    private val sharedPrefs: SharedPrefsManager by inject()

    private lateinit var movieState: AccountStates

    override fun getRootView(): View = window.decorView.rootView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        setWindowTransparency(::updateMargins)

        setSupportActionBar(md_toolbar)

        supportActionBar?.run {
            setDisplayUseLogoEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        fetchDetails(intent.extras)
        setClickListeners()
        setObserver()
    }

    private fun updateMargins(statusBarSize: Int, navigationBarSize: Int) {
        md_toolbar.updateMargin(top = statusBarSize)
    }

    override fun onSlidingFinished() {}

    override fun onSlidingStarted() {}

    override fun canSlideDown() =
        BottomSheetBehavior.from(md_bottom_sheet).state == BottomSheetBehavior.STATE_COLLAPSED

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_page_menu, menu)
        return true
    }

    private fun fetchDetails(extras: Bundle?) {
        if (extras != null) {
            movieDetailsViewModel.fetchMovieDetails(
                extras.getInt("id"),
                "ru",
                "images,credits,videos",
                "ru,en,null"
            )

            movieDetailsViewModel.fetchAccountStateForMovie(
                extras.getInt("id"),
                sharedPrefs.getTmdbSessionId()
            )
        }
    }

    private fun setClickListeners() {
        toggle_favorite.setOnClickListener {
            movieDetailsViewModel.markMovieIsFavorite(
                movieState.id,
                !movieState.favorite,
                sharedPrefs.getTmdbSessionId()
            )
        }

        toggle_watchlist.setOnClickListener {
            movieDetailsViewModel.addMovieToWatchlist(
                movieState.id,
                !movieState.watchlist,
                sharedPrefs.getTmdbSessionId()
            )
        }
    }

    private fun setObserver() {
        movieDetailsViewModel.movieDetailsLiveData.observe(this, Observer { movie ->
            placeholder_main_details_page.setVisibilityOption(false)

            main_layout.setVisibilityOption(true)

            val numFormat = DecimalFormat("#,###,###")
            m_title.text = movie.title
            m_original_language.text = movie.originalLanguage
            m_original_title.text = movie.originalTitle
            m_revenue.text = (numFormat.format(movie.revenue) + " $")
            m_budget.text = (numFormat.format(movie.budget) + " $")
            m_release_date.text = movie.releaseDate
            m_status.text = movie.status
            m_runtime.text = movie.runtime.toString()
            m_overview.text = movie.overview
            m_vote_average.text = movie.voteAverage.toString()

            m_companies.text = movie.productionCompanies.joinToString(", ") { it.name }

            m_backdrop_image.displayImageWithCenterCrop(UrlConstants.TMDB_BACKDROP_SIZE_780 + movie.backdropPath)
            m_poster_image.displayImageWithCenterCrop(UrlConstants.TMDB_POSTER_SIZE_500 + movie.posterPath)

            setImageSlider(movie.images.backdrops.map { imageInfo -> imageInfo.filePath }.take(6))

            m_persons.setAdapterWithFixedSize(PersonAdapter(movie.credits.casts), true)
            m_count_persons.text = movie.credits.casts.size.toString()
            m_count_trailers.text = movie.videos.results.size.toString()

            setTrailerButtonClickListener(movie.videos)
        })

        movieDetailsViewModel.movieStatesLiveData.observe(this, Observer {
            it?.apply {
                movieState = this
                toggle_favorite.isChecked = this.favorite
                toggle_watchlist.isChecked = this.watchlist
            }
        })

        movieDetailsViewModel.responseFavoriteLiveData.observe(this, Observer {
            if (it.statusMessage != "success") {
                Toast.makeText(this, "Неудалось добавить фильм в избранное", Toast.LENGTH_LONG)
                    .show()
            }
        })

        movieDetailsViewModel.responseWatchlistLiveData.observe(this, Observer {
            if (it.statusMessage != "success") {
                Toast.makeText(
                    this,
                    "Неудалось добавить фильм в 'Буду смотреть'",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun setTrailerButtonClickListener(videos: Videos) {
        if (videos.results.isNotEmpty()) {
            btn_watch_trailer.setOnClickListener {
                openYouTube(videos.results[0].key)
            }
        }
    }

    private fun setImageSlider(images: List<String>) {
        md_image_slider.adapter = ImageSliderAdapter(images)
    }
}

