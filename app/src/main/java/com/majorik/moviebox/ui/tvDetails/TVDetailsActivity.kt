package com.majorik.moviebox.ui.tvDetails

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
import kotlinx.android.synthetic.main.activity_tv_details.*
import kotlinx.android.synthetic.main.layout_tv_details.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.DecimalFormat

class TVDetailsActivity : BaseSlidingActivity() {
    private val tvDetailsViewModel: TVDetailsViewModel by viewModel()
    private val sharedPrefs: SharedPrefsManager by inject()

    private lateinit var tvState: AccountStates

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
                "ru",
                "images,credits,videos",
                "ru,en,null"
            )

            tvDetailsViewModel.fetchAccountStateForTV(
                extras.getInt("id"),
                sharedPrefs.getTmdbSessionId()
            )
        }
    }

    private fun setClickListeners() {
        toggle_favorite.setOnClickListener {
            tvDetailsViewModel.markTVIsFavorite(
                tvState.id,
                !tvState.favorite,
                sharedPrefs.getTmdbSessionId()
            )
        }

        toggle_watchlist.setOnClickListener {
            tvDetailsViewModel.addTVToWatchlist(
                tvState.id,
                !tvState.watchlist,
                sharedPrefs.getTmdbSessionId()
            )
        }
    }

    private fun setObserver() {
        tvDetailsViewModel.tvDetailsLiveData.observe(this, Observer { tv ->
            val numFormat = DecimalFormat("#,###,###")
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

            t_persons.setAdapterWithFixedSize(PersonAdapter(tv.credits.casts), true)
            t_count_persons.text = tv.credits.casts.size.toString()
            t_count_trailers.text = tv.videos.results.size.toString()

            setTrailerButtonClickListener(tv.videos)
        })

        tvDetailsViewModel.tvStatesLiveData.observe(this, Observer {
            it?.apply {
                tvState = this
                toggle_favorite.isChecked = this.favorite
                toggle_watchlist.isChecked = this.watchlist
            }
        })

        tvDetailsViewModel.responseFavoriteLiveData.observe(this, Observer {
            if (it.statusMessage != "success") {
                Toast.makeText(this, "Неудалось добавить сериал в избранное", Toast.LENGTH_LONG)
                    .show()
            }
        })

        tvDetailsViewModel.responseWatchlistLiveData.observe(this, Observer {
            if (it.statusMessage != "success") {
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
