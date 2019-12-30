package com.majorik.moviebox.ui.tvDetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.majorik.domain.tmdbModels.account.AccountStates
import com.majorik.domain.tmdbModels.video.Video
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.*
import com.majorik.moviebox.extensions.setAdapterWithFixedSize
import com.majorik.moviebox.utils.SharedPrefsManager
import kotlinx.android.synthetic.main.activity_tv_details.*
import kotlinx.android.synthetic.main.activity_tv_details.company_list
import kotlinx.android.synthetic.main.activity_tv_details.expand_text_view
import kotlinx.android.synthetic.main.activity_tv_details.image_pager
import kotlinx.android.synthetic.main.activity_tv_details.is_favorite_button
import kotlinx.android.synthetic.main.activity_tv_details.is_watchlist_button
import kotlinx.android.synthetic.main.activity_tv_details.trailer_pager
import kotlinx.android.synthetic.main.activity_tv_details.worm_dots_indicator
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class TVDetailsActivity : AppCompatActivity() {
    private val tvDetailsViewModel: TVDetailsViewModel by viewModel()
    private val sharedPrefs: SharedPrefsManager by inject()

    private lateinit var tvState: AccountStates

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_details)

        setSupportActionBar(toolbar_tv_details)

        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        fetchDetails(intent.extras)
        setClickListener()
        setObserver()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setObserver() {
        tvDetailsViewModel.tvDetailsLiveData.observe(this, Observer { tv ->
            tv_title.text = tv.name
            expand_text_view.text = tv.overview
            tv_original_language.text = tv.originalLanguage
            tv_original_title.text = tv.originalName
            tv_status.text = tv.status
            tv_runtime.text = tv.episodeRunTime[0].toString()
            tv_first_air_date.text = tv.firstAirDate
            tv_date_last_air.text = tv.lastAirDate
            tv_count_seasons.text = tv.numberOfSeasons.toString()
            tv_count_episodes.text = tv.numberOfEpisodes.toString()

            setImageSlider(tv.images.backdrops.map { imageInfo -> imageInfo.filePath }.take(6))
            company_list.adapter = CompaniesAdapter(tv.productionCompanies)
            setTrailerSlider(tv.videos.results)
            tv_casts.adapter = PersonAdapter(tv.credits.casts)
            tv_seasons_list.setAdapterWithFixedSize(SeasonAdapter(tv.id, tv.seasons), true)
        })

        tvDetailsViewModel.tvStatesLiveData.observe(this, Observer {
            it?.run {
                tvState = this
                is_favorite_button.isChecked = this.favorite
                is_watchlist_button.isChecked = this.watchlist
            }
        })
    }

    private fun setClickListener() {
        is_favorite_button.setOnClickListener {
            tvDetailsViewModel.markTVIsFavorite(
                tvState.id,
                !tvState.favorite,
                sharedPrefs.getTmdbSessionId()
            )
        }

        is_watchlist_button.setOnClickListener {
            tvDetailsViewModel.addTVToWatchlist(
                tvState.id,
                !tvState.watchlist,
                sharedPrefs.getTmdbSessionId()
            )
        }
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

    private fun setImageSlider(images: List<String>) {
        image_pager.adapter = ImageSliderAdapter(images)
        image_pager.pageMargin = ((16 * resources.displayMetrics.density).toInt())
        worm_dots_indicator.setViewPager(image_pager)
    }

    private fun setTrailerSlider(videos: List<Video>) {
        trailer_pager.adapter = VideoAdapter(videos)
        trailer_pager.pageMargin = ((16 * resources.displayMetrics.density).toInt())
    }
}
