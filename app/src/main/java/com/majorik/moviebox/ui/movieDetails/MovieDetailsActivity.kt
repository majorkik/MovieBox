package com.majorik.moviebox.ui.movieDetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.majorik.domain.tmdbModels.account.AccountStates
import com.majorik.domain.tmdbModels.other.Video
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.CompaniesAdapter
import com.majorik.moviebox.adapters.ImageSliderAdapter
import com.majorik.moviebox.adapters.PersonAdapter
import com.majorik.moviebox.adapters.VideoAdapter
import com.majorik.moviebox.extensions.setAdapterWithFixedSize
import com.majorik.moviebox.utils.SharedPrefsManager
import kotlinx.android.synthetic.main.activity_movie_details.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.DecimalFormat

class MovieDetailsActivity : AppCompatActivity() {
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModel()
    private val sharedPrefs: SharedPrefsManager by inject()

    private lateinit var movieState: AccountStates

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        setSupportActionBar(toolbar_movie_details)

        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        fetchDetails(intent.extras)
        setClickListeners()
        setObserver()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setClickListeners() {
        is_favorite_button.setOnClickListener {
            movieDetailsViewModel.markMovieIsFavorite(
                movieState.id,
                !movieState.favorite,
                sharedPrefs.getTmdbSessionId()
            )
        }

        is_watchlist_button.setOnClickListener {
            movieDetailsViewModel.addMovieToWatchlist(
                movieState.id,
                !movieState.watchlist,
                sharedPrefs.getTmdbSessionId()
            )
        }
    }

    private fun setObserver() {
        movieDetailsViewModel.movieDetailsLiveData.observe(this, Observer { movie ->
            val numFormat = DecimalFormat("#,###,###")
            movie_title.text = movie.title
            movie_original_language.text = movie.originalLanguage
            movie_original_title.text = movie.originalTitle
            movie_revenue.text = (numFormat.format(movie.revenue) + " $")
            movie_budget.text = (numFormat.format(movie.budget) + " $")
            movie_date_release.text = movie.releaseDate
            movie_status.text = movie.status
            movie_runtime.text = movie.runtime.toString()
            expand_text_view.text = movie.overview

            setImageSlider(movie.images.backdrops.map { imageInfo -> imageInfo.filePath }.take(6))
            company_list.setAdapterWithFixedSize(CompaniesAdapter(movie.productionCompanies), true)
            setTrailerSlider(movie.videos.results)
            movie_casts.setAdapterWithFixedSize(PersonAdapter(movie.credits.casts), true)
        })

        movieDetailsViewModel.movieStatesLiveData.observe(this, Observer {
            it?.run{
                movieState = this
                is_favorite_button.isChecked = this.favorite
                is_watchlist_button.isChecked = this.watchlist
            }
        })
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
