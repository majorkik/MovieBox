package com.majorik.moviebox.feature.details.presentation.movieDetails

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.majorik.library.base.base.BaseSlidingActivity
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.*
import com.majorik.library.base.storage.CredentialsPrefsManager
import com.majorik.moviebox.feature.details.R
import com.majorik.moviebox.feature.details.domain.tmdbModels.account.AccountStates
import com.majorik.moviebox.feature.details.domain.tmdbModels.cast.Cast
import com.majorik.moviebox.feature.details.domain.tmdbModels.genre.Genre
import com.majorik.moviebox.feature.details.domain.tmdbModels.image.Images
import com.majorik.moviebox.feature.details.domain.tmdbModels.movie.MovieDetails
import com.majorik.moviebox.feature.details.domain.tmdbModels.production.ProductionCompany
import com.majorik.moviebox.feature.details.domain.tmdbModels.video.Videos
import com.majorik.moviebox.feature.details.presentation.adapters.CastAdapter
import com.majorik.moviebox.feature.details.presentation.adapters.ImageSliderAdapter
import com.majorik.moviebox.feature.details.presentation.watch_online.WatchOnlineDialog
import com.soywiz.klock.KlockLocale
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

        fetchDetails(intent.extras)
        setClickListeners()
        setObserver()
    }

    private fun updateMargins(statusBarSize: Int, navigationBarSize: Int) {
        bottom_bar.updateMargin(bottom = navigationBarSize)
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

    private fun setObserver() {
        movieDetailsViewModel.movieDetailsLiveData.observe(
            this,
            Observer { movie ->
                setVisibilityPlaceholder(false)

                setHeader(movie.title, movie.voteAverage, movie.status, movie.genres, movie.releaseDate)
                setOverview(movie.overview)
                setFacts(movie)
                setImages(movie.images, movie.backdropPath, movie.posterPath)
                setPeoples(movie.credits.casts)
                setTrailerButtonClickListener(movie.videos)
            }
        )

        movieDetailsViewModel.movieStatesLiveData.observe(
            this,
            Observer {
                it?.apply { setAccountStates(this) }
            }
        )

        movieDetailsViewModel.responseFavoriteLiveData.observe(
            this,
            Observer {
                if (it.statusCode == 1 || it.statusCode == 12 || it.statusCode == 13) {
                    showToastMessage("Фильм успешно добавлен в избранное")
                } else {
                    showToastMessage("Неудалось добавить фильм в избранное")
                }
            }
        )

        movieDetailsViewModel.responseWatchlistLiveData.observe(
            this,
            Observer {
                if (it.statusCode == 1 || it.statusCode == 12 || it.statusCode == 13) {
                    showToastMessage("Фильм успешно добавлен в 'Буду смотреть'")
                } else {
                    showToastMessage("Неудалось добавить фильм в 'Буду смотреть'")
                }
            }
        )
    }

    private fun setPeoples(casts: List<Cast>) {
        m_persons.setAdapterWithFixedSize(CastAdapter(casts), true)
    }

    private fun setImages(images: Images, backdropPath: String?, posterPath: String?) {
        m_backdrop_image.displayImageWithCenterCrop(UrlConstants.TMDB_BACKDROP_SIZE_780 + backdropPath)
        m_poster_image.displayImageWithCenterCrop(UrlConstants.TMDB_POSTER_SIZE_500 + posterPath)
        setImageSlider(images.backdrops.map { imageInfo -> imageInfo.filePath }.take(6))
        setClickListenerForImages(images)
    }

    private fun setFacts(movie: MovieDetails?) {
        if (movie == null) return

        setOriginalLanguage(movie.originalLanguage)
        setOriginalTitle(movie.originalTitle)
        setRevenue(movie.revenue)
        setBudget(movie.budget)
        setReleaseDate(movie.releaseDate)
        setRuntime(movie.runtime)
        setCompanies(movie.productionCompanies)
    }

    private fun setAccountStates(accountStates: AccountStates) {
        movieState = accountStates
        toggle_favorite.isChecked = accountStates.favorite
        toggle_watchlist.isChecked = accountStates.watchlist
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

        btn_extra_menu.setSafeOnClickListener {
            openExtraMenuDialog()
        }

        bottom_bar.setSafeOnClickListener {
            openWatchOnlineDialog()
        }
    }

    private fun openWatchOnlineDialog() {
        val watchOnlineDialog = WatchOnlineDialog()
        watchOnlineDialog.show(supportFragmentManager, "watch_online_dialog")
    }

    private fun openExtraMenuDialog() {
        val extraMenuBottomDialog = MovieExtraMenuBottomDialog()
        extraMenuBottomDialog.show(supportFragmentManager, "extra_menu_dialog")
    }

    private fun setClickListenerForImages(images: Images) {
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

    private fun setVisibilityPlaceholder(isVisible: Boolean) {
        placeholder_main_details_page.setVisibilityOption(isVisible)
        main_layout.setVisibilityOption(!isVisible)
    }

    private fun setHeader(
        title: String,
        voteAverage: Double,
        status: String,
        genres: List<Genre>,
        releaseDate: String
    ) {
        m_title.text = title
        m_vote_average.text = voteAverage.toString()
        m_status.text = combineString(getString(R.string.details_status), status)
        vote_average_indicator.setIndicatorColor(voteAverage)

        setGenres(genres, releaseDate)
    }

    private fun setCompanies(productionCompanies: List<ProductionCompany>) {
        m_companies.text = combineString(
            getString(R.string.details_company_production),
            productionCompanies.joinToString(", ") { it.name }
        )
    }

    private fun setOriginalTitle(originalTitle: String) {
        m_original_title.text = combineString(getString(R.string.details_original_title), originalTitle)
    }

    private fun setRevenue(revenue: Long) {
        val numFormat = DecimalFormat("#,###,###")

        m_revenue.text =
            combineString(getString(R.string.details_revenue), (numFormat.format(revenue) + " $"))
    }

    private fun setBudget(budget: Int) {
        val numFormat = DecimalFormat("#,###,###")

        m_budget.text = combineString(getString(R.string.details_budget), (numFormat.format(budget) + " $"))
    }

    private fun setReleaseDate(releaseDate: String) {
        m_release_date.text =
            combineString(
                getString(R.string.details_release_date),
                KlockLocale.default.formatDateLong.format(releaseDate.toDate(getString(R.string.details_yyyy_mm_dd)))
            )
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun setOriginalLanguage(originalLanguage: String) {
        m_original_language.text =
            combineString(
                getString(R.string.details_original_language),
                Locale(originalLanguage).displayLanguage.capitalize(AppConfig.APP_LOCALE)
            )
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun setGenres(genres: List<Genre>, releaseDate: String) {
        val genresFormat = genres.joinToString(", ") { it.name.capitalize(AppConfig.APP_LOCALE) }

        m_add_info.text = getString(
            R.string.details_short_info_mask,
            releaseDate.toDate(getString(R.string.details_yyyy_mm_dd)).yearInt.toString(),
            genresFormat
        )
    }

    private fun setRuntime(runtime: Int?) {
        if (runtime != null) {
            val hours = floor(runtime / 60.0).toInt()
            val stringHours =
                resources.getQuantityString(R.plurals.hours, hours, hours)

            val minutes = runtime % 60
            val stringMinutes =
                resources.getQuantityString(R.plurals.minutes, minutes, minutes)

            m_runtime.text = combineString(
                getString(R.string.details_runtime),
                getString(
                    R.string.details_runtime_mask,
                    stringHours,
                    stringMinutes
                )
            )
        } else {
            m_runtime.text = combineString(
                getString(R.string.details_runtime),
                getString(R.string.details_unknown)
            )
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

    private fun setImageSlider(images: List<String>) {
        md_image_slider.adapter = ImageSliderAdapter(images)
    }

    private fun setTrailerButtonClickListener(videos: Videos) {
        if (videos.results.isNotEmpty()) {
            btn_watch_trailer.setOnClickListener {
                openYouTube(videos.results[0].key)
            }
        }
    }
}
