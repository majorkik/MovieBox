package com.majorik.moviebox.ui.movieDetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.majorik.domain.models.other.ProductionCompany
import com.majorik.domain.models.other.Video
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.CompaniesAdapter
import com.majorik.moviebox.adapters.ImageSliderAdapter
import com.majorik.moviebox.adapters.PersonAdapter
import com.majorik.moviebox.adapters.VideoAdapter
import kotlinx.android.synthetic.main.activity_movie_details.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.DecimalFormat

class MovieDetailsActivity : AppCompatActivity() {
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        setSupportActionBar(toolbar_movie_details)
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
        movieDetailsViewModel.movieDetailsLiveData.observe(this, Observer { movie ->
            val numFormat = DecimalFormat("#,###,###")
            movie_title.text = movie.title
            movie_vote_average.text = movie.voteAverage.toString()
            movie_vote_count.text = movie.voteCount.toString()
            movie_original_language.text = movie.originalLanguage
            movie_original_title.text = movie.originalTitle
            movie_revenue.text = (numFormat.format(movie.revenue) + " $")
            movie_budget.text = (numFormat.format(movie.budget) + " $")
            movie_date_release.text = movie.releaseDate
            movie_status.text = movie.status
            movie_popularity.text = movie.popularity.toString()
            movie_runtime.text = movie.runtime.toString()
            expand_text_view.text = movie.overview

            setImageSlider(movie.images.backdrops.map { imageInfo -> imageInfo.filePath }.take(6))
            company_list.adapter = CompaniesAdapter(movie.productionCompanies)
            setTrailerSlider(movie.videos.results)
            movie_casts.adapter = PersonAdapter(movie.credits.casts)
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
