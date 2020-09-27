package com.majorik.moviebox.feature.details.presentation.tvDetails

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
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
import com.majorik.moviebox.feature.details.domain.tmdbModels.production.ProductionCompany
import com.majorik.moviebox.feature.details.domain.tmdbModels.tv.TVDetails
import com.majorik.moviebox.feature.details.domain.tmdbModels.video.Videos
import com.majorik.moviebox.feature.details.presentation.adapters.CastAdapter
import com.majorik.moviebox.feature.details.presentation.adapters.ImageSliderAdapter
import com.majorik.moviebox.feature.details.presentation.watch_online.WatchOnlineDialog
import com.soywiz.klock.KlockLocale
import com.stfalcon.imageviewer.StfalconImageViewer
import kotlinx.android.synthetic.main.activity_tv_details.*
import kotlinx.android.synthetic.main.activity_tv_details.bottom_bar
import kotlinx.android.synthetic.main.layout_tv_details.*
import kotlinx.android.synthetic.main.layout_tv_details.btn_watch_trailer
import kotlinx.android.synthetic.main.layout_tv_details.main_layout
import kotlinx.android.synthetic.main.layout_tv_details.placeholder_main_details_page
import kotlinx.android.synthetic.main.layout_tv_details.toggle_favorite
import kotlinx.android.synthetic.main.layout_tv_details.toggle_watchlist
import kotlinx.android.synthetic.main.layout_tv_details.vote_average_indicator
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class TVDetailsActivity : BaseSlidingActivity() {
    private val tvDetailsViewModel: TVDetailsViewModel by viewModel()
    private val sharedPrefs: CredentialsPrefsManager by inject()

    private var tvState: AccountStates? = null

    override fun getRootView(): View = window.decorView.rootView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_details)

        setWindowTransparency(::updateMargins)

        setSupportActionBar(td_toolbar)

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
        td_toolbar.updateMargin(top = statusBarSize)
    }

    override fun onSlidingStarted() {}

    override fun onSlidingFinished() {}

    override fun canSlideDown() =
        BottomSheetBehavior.from(td_bottom_sheet).state == BottomSheetBehavior.STATE_COLLAPSED

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
            tvDetailsViewModel.fetchTVDetails(
                extras.getInt("id"),
                AppConfig.REGION,
                "images,credits,videos",
                "ru,en,null"
            )

            tvDetailsViewModel.fetchAccountStateForTV(
                extras.getInt("id"),
                sharedPrefs.getTmdbSessionID() ?: ""
            )
        }
    }

    private fun setClickListeners() {
        toggle_favorite.setOnClickListener {
            tvState?.let {
                tvDetailsViewModel.markTVIsFavorite(
                    it.id,
                    !it.favorite,
                    sharedPrefs.getTmdbSessionID() ?: ""
                )
            }
        }

        toggle_watchlist.setOnClickListener {
            tvState?.let {
                tvDetailsViewModel.addTVToWatchlist(
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
        val extraMenuBottomDialog = TVExtraMenuBottomDialog()
        extraMenuBottomDialog.show(supportFragmentManager, "extra_menu_dialog")
    }

    private fun setClickListenerForImages(
        images: Images
    ) {
        t_backdrop_image.setOnClickListener {
            StfalconImageViewer.Builder(this, images.backdrops.map { it.filePath }) { view, image ->
                view.displayImageWithCenterInside(UrlConstants.TMDB_BACKDROP_SIZE_1280 + image)
            }.withHiddenStatusBar(false).show()
        }

        t_poster_image.setOnClickListener {
            StfalconImageViewer.Builder(this, images.posters.map { it.filePath }) { view, image ->
                view.displayImageWithCenterInside(UrlConstants.TMDB_BACKDROP_SIZE_1280 + image)
            }.withHiddenStatusBar(false).show()
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun setObserver() {
        tvDetailsViewModel.tvDetailsLiveData.observe(
            this,
            Observer { tv ->
                setHeader(tv.name, tv.voteAverage, tv.status, tv.genres, tv.firstAirDate)
                setVisibilityPlaceholder(false)
                setTrailerButtonClickListener(tv.videos)
                setOverview(tv.overview)
                setFacts(tv)
                setPeoples(tv.credits.casts)
                setImages(tv.images, tv.backdropPath, tv.posterPath)
            }
        )

        tvDetailsViewModel.tvStatesLiveData.observe(
            this,
            Observer {
                it?.apply { setAccountState(this) }
            }
        )

        tvDetailsViewModel.responseFavoriteLiveData.observe(
            this,
            Observer {
                if (it.statusCode == 1 || it.statusCode == 12 || it.statusCode == 13) {
                    Toast.makeText(this, "Сериал успешно добавлен в избранное", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(this, "Неудалось добавить сериал в избранное", Toast.LENGTH_LONG)
                        .show()
                }
            }
        )

        tvDetailsViewModel.responseWatchlistLiveData.observe(
            this,
            Observer {
                if (it.statusCode == 1 || it.statusCode == 12 || it.statusCode == 13) {
                    Toast.makeText(
                        this,
                        "Сериал успешно добавлен в 'Буду смотреть'",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "Неудалось добавить сериал в 'Буду смотреть'",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        )
    }

    private fun setAccountState(accountStates: AccountStates) {
        tvState = accountStates
        toggle_favorite.isChecked = accountStates.favorite
        toggle_watchlist.isChecked = accountStates.watchlist
    }

    private fun setPeoples(casts: List<Cast>) {
        t_persons.setAdapterWithFixedSize(CastAdapter(casts), true)
    }

    private fun setImages(images: Images, backdropPath: String?, posterPath: String?) {
        t_backdrop_image.displayImageWithCenterCrop(UrlConstants.TMDB_BACKDROP_SIZE_780 + backdropPath)
        t_poster_image.displayImageWithCenterCrop(UrlConstants.TMDB_POSTER_SIZE_500 + posterPath)

        setImageSlider(images.backdrops.map { imageInfo -> imageInfo.filePath }.take(6))
        setClickListenerForImages(images)
    }

    private fun setFacts(tv: TVDetails?) {
        if (tv == null) return

        setAirDate(tv.firstAirDate, tv.lastAirDate)
        setOriginalLanguage(tv.originalLanguage)
        setOriginalTitle(tv.originalName)
        setSeasonsAndEpisodes(tv.numberOfSeasons, tv.numberOfEpisodes)
        setRuntime(tv.episodeRunTime)
        setProductionCompanies(tv.productionCompanies)
    }

    private fun setProductionCompanies(productionCompanies: List<ProductionCompany>) {
        t_companies.text = combineString(
            getString(R.string.details_company_production),
            productionCompanies.joinToString(", ") { it.name }
        )
    }

    private fun setOverview(overview: String) {
        if (overview.isBlank()) {
            t_overview_empty.setVisibilityOption(true)
            t_overview.setVisibilityOption(false)
        } else {
            t_overview_empty.setVisibilityOption(false)
            t_overview.setVisibilityOption(true)
            t_overview.text = overview
        }
    }

    private fun setRuntime(episodeRunTime: List<Int>) {
        t_runtime.text = combineString(getString(R.string.details_runtime), episodeRunTime.toString())
    }

    private fun setSeasonsAndEpisodes(numberOfSeasons: Int, numberOfEpisodes: Int) {
        t_seasons_and_series.text =
            combineString(
                getString(R.string.details_fact_seasons_and_episodes),
                ("$numberOfSeasons сезон(ов) $numberOfEpisodes серий")
            )
    }

    private fun setOriginalTitle(originalName: String) {
        t_original_title.text = combineString(getString(R.string.details_original_title), originalName)
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun setOriginalLanguage(originalLanguage: String) {
        t_original_language.text = combineString(
            getString(R.string.details_original_language),
            Locale(originalLanguage).displayLanguage.capitalize(AppConfig.APP_LOCALE)
        )
    }

    private fun setHeader(
        title: String,
        voteAverage: Double,
        status: String,
        genres: List<Genre>,
        releaseDate: String
    ) {
        t_title.text = title
        t_vote_average.text = voteAverage.toString()
        t_status.text = combineString(getString(R.string.details_status), status)
        vote_average_indicator.setIndicatorColor(voteAverage)

        setGenres(genres, releaseDate)
    }

    private fun setAirDate(firstAirDate: String, lastAirDate: String) {
        val klockInstance = KlockLocale.default.formatDateLong

        val dateFormat = getString(R.string.details_yyyy_mm_dd)

        val formatFirstAirDate = klockInstance.format(firstAirDate.toDate(dateFormat))
        val formatLastAirDate = klockInstance.format(lastAirDate.toDate(dateFormat))

        t_first_air_date.text = combineString(getString(R.string.details_fact_first_air_date), formatFirstAirDate)
        t_last_air_date.text = combineString(getString(R.string.details_fact_last_air_date), formatLastAirDate)
    }

    private fun setTrailerButtonClickListener(videos: Videos) {
        if (videos.results.isNotEmpty()) {
            btn_watch_trailer.setOnClickListener {
                openYouTube(videos.results[0].key)
            }
        }
    }

    private fun setImageSlider(images: List<String>) {
        td_image_slider.adapter = ImageSliderAdapter(images)
    }

    private fun setVisibilityPlaceholder(isVisible: Boolean) {
        placeholder_main_details_page.setVisibilityOption(isVisible)
        main_layout.setVisibilityOption(!isVisible)
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun setGenres(genres: List<Genre>, releaseDate: String) {
        val genresFormat = genres.joinToString(", ") { it.name.capitalize(AppConfig.APP_LOCALE) }

        t_add_info.text = getString(
            R.string.details_short_info_mask,
            releaseDate.toDate(getString(R.string.details_yyyy_mm_dd)).yearInt.toString(),
            genresFormat
        )
    }
}
