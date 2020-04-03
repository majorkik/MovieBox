package com.majorik.feature.details.presentation.tvDetails

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.majorik.feature.details.R
import com.majorik.feature.details.domain.tmdbModels.account.AccountStates
import com.majorik.feature.details.domain.tmdbModels.image.Images
import com.majorik.feature.details.domain.tmdbModels.video.Videos
import com.majorik.feature.details.presentation.adapters.CastAdapter
import com.majorik.moviebox.adapters.ImageSliderAdapter
import com.majorik.library.base.extensions.*
import com.majorik.library.base.storage.SharedPrefsManager
import com.majorik.library.base.base.BaseSlidingActivity
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.constants.UrlConstants
import com.stfalcon.imageviewer.StfalconImageViewer
import kotlinx.android.synthetic.main.activity_tv_details.*
import kotlinx.android.synthetic.main.layout_tv_details.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TVDetailsActivity : BaseSlidingActivity() {
    private val tvDetailsViewModel: TVDetailsViewModel by viewModel()
    private val sharedPrefs: SharedPrefsManager by inject()

    private var tvState: AccountStates? = null

    override fun getRootView(): View = window.decorView.rootView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.majorik.feature.details.R.layout.activity_tv_details)

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

    private fun updateMargins(statusBarSize: Int, @Suppress("UNUSED_PARAMETER") navigationBarSize: Int) {
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
        menuInflater.inflate(com.majorik.feature.details.R.menu.details_page_menu, menu)
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
                sharedPrefs.getTmdbSessionID()
            )
        }
    }

    private fun setClickListeners() {
        toggle_favorite.setOnClickListener {
            tvState?.let {
                tvDetailsViewModel.markTVIsFavorite(
                    it.id,
                    !it.favorite,
                    sharedPrefs.getTmdbSessionID()
                )
            }
        }

        toggle_watchlist.setOnClickListener {
            tvState?.let {
                tvDetailsViewModel.addTVToWatchlist(
                    it.id,
                    !it.watchlist,
                    sharedPrefs.getTmdbSessionID()
                )
            }
        }
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

    private fun setObserver() {
        tvDetailsViewModel.tvDetailsLiveData.observe(this, Observer { tv ->
            placeholder_main_details_page.setVisibilityOption(false)

            main_layout.setVisibilityOption(true)

            t_title.text = tv.name
            t_original_language.text = tv.originalLanguage
            t_original_title.text = tv.originalName
            t_first_air_date.text = tv.firstAirDate
            t_last_air_date.text = tv.lastAirDate
            t_seasons_and_series.text =
                ("${tv.numberOfSeasons} сезон(ов) ${tv.numberOfEpisodes} серий")
            t_status.text = tv.status
            t_runtime.text = tv.episodeRunTime.toString()
            t_overview.text = tv.overview
            t_vote_average.text = tv.voteAverage.toString()

            t_companies.text = tv.productionCompanies.joinToString(", ") { it.name }

            t_backdrop_image.displayImageWithCenterCrop(UrlConstants.TMDB_BACKDROP_SIZE_780 + tv.backdropPath)
            t_poster_image.displayImageWithCenterCrop(UrlConstants.TMDB_POSTER_SIZE_500 + tv.posterPath)

            setImageSlider(tv.images.backdrops.map { imageInfo -> imageInfo.filePath }.take(6))

            t_persons.setAdapterWithFixedSize(CastAdapter(tv.credits.casts), true)
            t_count_persons.text = tv.credits.casts.size.toString()
            t_count_trailers.text = tv.videos.results.size.toString()

            setTrailerButtonClickListener(tv.videos)

            setClickListenerForImages(tv.images)
        })

        tvDetailsViewModel.tvStatesLiveData.observe(this, Observer {
            it?.apply {
                tvState = this
                toggle_favorite.isChecked = this.favorite
                toggle_watchlist.isChecked = this.watchlist
            }
        })

        tvDetailsViewModel.responseFavoriteLiveData.observe(this, Observer {
            if (it.statusCode == 1 || it.statusCode == 12 || it.statusCode == 13) {
                Toast.makeText(this, "Сериал успешно добавлен в избранное", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(this, "Неудалось добавить сериал в избранное", Toast.LENGTH_LONG)
                    .show()
            }
        })

        tvDetailsViewModel.responseWatchlistLiveData.observe(this, Observer {
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
        td_image_slider.adapter = ImageSliderAdapter(images)
    }
}
