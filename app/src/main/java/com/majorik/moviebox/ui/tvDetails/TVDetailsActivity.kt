package com.majorik.moviebox.ui.tvDetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.majorik.domain.tmdbModels.other.Video
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.*
import com.majorik.moviebox.extensions.setAdapterWithFixedSize
import kotlinx.android.synthetic.main.activity_tv_details.*
import org.koin.android.viewmodel.ext.android.viewModel

class TVDetailsActivity : AppCompatActivity() {
    private val tvDetailsViewModel: TVDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_details)

        setSupportActionBar(toolbar_tv_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        fetchDetails(intent.extras)

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
            tv_seasons_list.setAdapterWithFixedSize(SeasonAdapter(tv.id,tv.seasons), true)
        })
    }

    private fun fetchDetails(extras: Bundle?) {
        if (extras != null) {
            tvDetailsViewModel.fetchTVDetails(
                extras.getInt("id"),
                "ru",
                "images,credits,videos",
                "ru,en,null"
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
