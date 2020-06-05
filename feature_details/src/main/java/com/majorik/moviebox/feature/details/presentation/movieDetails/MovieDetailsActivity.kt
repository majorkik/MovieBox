package com.majorik.moviebox.feature.details.presentation.movieDetails

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.majorik.library.base.base.BaseSlidingActivity
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.*
import com.majorik.library.base.storage.CredentialsPrefsManager
import com.majorik.moviebox.feature.details.R
import com.majorik.moviebox.feature.details.databinding.ActivityMovieDetailsBinding
import com.majorik.moviebox.feature.details.databinding.LayoutMovieDetailsBinding
import com.majorik.moviebox.feature.details.domain.tmdbModels.account.AccountStates
import com.majorik.moviebox.feature.details.domain.tmdbModels.image.Images
import com.majorik.moviebox.feature.details.domain.tmdbModels.video.Videos
import com.majorik.moviebox.feature.details.presentation.adapters.CastAdapter
import com.majorik.moviebox.feature.details.presentation.adapters.ImageSliderAdapter
import com.soywiz.klock.KlockLocale
import com.soywiz.klock.locale.russian
import com.stfalcon.imageviewer.StfalconImageViewer
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.layout_movie_details.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat
import java.util.*
import kotlin.math.floor

class MovieDetailsActivity : BaseSlidingActivity() {

    private val movieDetailsViewModel: MovieDetailsViewModel by viewModel()

    private val sharedPrefs: CredentialsPrefsManager by inject()

    private var movieState: AccountStates? = null

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
//
//         cacheDir?.deleteRecursively()
//         codeCacheDir?.deleteRecursively()
//         externalCacheDir?.deleteRecursively()
//         externalCacheDirs?.forEach { it.deleteRecursively() }

        fetchDetails(intent.extras)
        setClickListeners()
        setObserver()
    }

    private fun updateMargins(
        statusBarSize: Int,
        @Suppress("UNUSED_PARAMETER") navigationBarSize: Int
    ) {
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
                AppConfig.REGION,
                "images,credits,videos",
                "ru,en,null"
            )

            movieDetailsViewModel.fetchAccountStateForMovie(
                extras.getInt("id"),
                sharedPrefs.getTmdbSessionID() ?: ""
            )
        }
    }

    private fun setClickListeners() {
        toggle_favorite.setOnClickListener {
            movieState?.let {
                movieDetailsViewModel.markMovieIsFavorite(
                    it.id,
                    !it.favorite,
                    sharedPrefs.getTmdbSessionID() ?: ""
                )
            }
        }

        toggle_watchlist.setOnClickListener {
            movieState?.let {
                movieDetailsViewModel.addMovieToWatchlist(
                    it.id,
                    !it.watchlist,
                    sharedPrefs.getTmdbSessionID() ?: ""
                )
            }
        }
    }

    private fun setClickListenerForImages(
        images: Images
    ) {

        m_backdrop_image.setOnClickListener {
            StfalconImageViewer.Builder(this, images.backdrops.map { it.filePath }) { view, image ->
                view.displayImageWithCenterInside(UrlConstants.TMDB_BACKDROP_SIZE_1280 + image)
            }.withOverlayView(
                layoutInflater.inflate(
                    R.layout.item_genre_inline_details,
                    null
                )
            )
                .withHiddenStatusBar(false)
                .show()
        }

        m_poster_image.setOnClickListener {
            StfalconImageViewer.Builder(this, images.posters.map { it.filePath }) { view, image ->
                view.displayImageWithCenterInside(UrlConstants.TMDB_BACKDROP_SIZE_1280 + image)
            }.withHiddenStatusBar(false).show()
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun setObserver() {
        movieDetailsViewModel.movieDetailsLiveData.observe(this, Observer { movie ->
            placeholder_main_details_page.setVisibilityOption(false)

            main_layout.setVisibilityOption(true)

            val numFormat = DecimalFormat("#,###,###")
            m_title.text = movie.title
            m_original_language.text =
                Locale(movie.originalLanguage).displayLanguage.capitalize(AppConfig.APP_LOCALE)
            m_original_title.text = movie.originalTitle
            m_revenue.text = (numFormat.format(movie.revenue) + " $")
            m_budget.text = (numFormat.format(movie.budget) + " $")
            m_release_date.text =
                KlockLocale.russian.formatDateLong.format(movie.releaseDate.toDate(getString(R.string.details_yyyy_mm_dd)))
            m_status.text = movie.status
            setReleaseDate(movie.runtime)
            setOverview(movie.overview)
            m_vote_average.text = movie.voteAverage.toString()

            val genres =
                movie.genres.joinToString(", ") { it.name.capitalize(AppConfig.APP_LOCALE) }

            m_add_info.text = getString(
                R.string.details_short_info_mask,
                movie.releaseDate.toDate(getString(R.string.details_yyyy_mm_dd)).yearInt.toString(),
                genres
            )

            m_companies.text = movie.productionCompanies.joinToString(", ") { it.name }

            vote_average_indicator.setIndicatorColor(movie.voteAverage)

            m_backdrop_image.displayImageWithCenterCrop(UrlConstants.TMDB_BACKDROP_SIZE_780 + movie.backdropPath)
            m_poster_image.displayImageWithCenterCrop(UrlConstants.TMDB_POSTER_SIZE_500 + movie.posterPath)

            setImageSlider(movie.images.backdrops.map { imageInfo -> imageInfo.filePath }.take(6))

            m_persons.setAdapterWithFixedSize(CastAdapter(movie.credits.casts), true)

            setTrailerButtonClickListener(movie.videos)

            setClickListenerForImages(movie.images)
        })

        movieDetailsViewModel.movieStatesLiveData.observe(this, Observer {
            it?.apply {
                movieState = this
                toggle_favorite.isChecked = this.favorite
                toggle_watchlist.isChecked = this.watchlist
            }
        })

        movieDetailsViewModel.responseFavoriteLiveData.observe(this, Observer {
            if (it.statusCode == 1 || it.statusCode == 12 || it.statusCode == 13) {
                showToastMessage("Фильм успешно добавлен в избранное")
            } else {
                showToastMessage("Неудалось добавить фильм в избранное")
            }
        })

        movieDetailsViewModel.responseWatchlistLiveData.observe(this, Observer {
            if (it.statusCode == 1 || it.statusCode == 12 || it.statusCode == 13) {
                showToastMessage("Фильм успешно добавлен в 'Буду смотреть'")
            } else {
                showToastMessage("Неудалось добавить фильм в 'Буду смотреть'")
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

    private fun setReleaseDate(runtime: Int?) {
        if (runtime != null) {
            val hours = floor(runtime / 60.0).toInt()
            val stringHours =
                resources.getQuantityString(R.plurals.hours, hours, hours)

            val minutes = runtime % 60
            val stringMinutes =
                resources.getQuantityString(R.plurals.minutes, minutes, minutes)

            m_runtime.text = getString(
                R.string.details_runtime_mask,
                stringHours,
                stringMinutes
            )
        } else {
            m_runtime.text = getString(R.string.details_unknown)
        }
    }

    private fun setOverview(overview: String?) {
        if (overview.isNullOrBlank()) {
            m_overview_empty.setVisibilityOption(true)
            m_overview.setVisibilityOption(false)
        } else {
            m_overview_empty.setVisibilityOption(false)
            m_overview.setVisibilityOption(true)
            m_overview.text = overview
        }
    }
}
