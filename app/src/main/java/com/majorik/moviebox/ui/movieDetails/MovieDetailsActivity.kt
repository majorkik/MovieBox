package com.majorik.moviebox.ui.movieDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.ImageSliderAdapter
import com.majorik.moviebox.ui.movie.MovieViewModel
import kotlinx.android.synthetic.main.activity_movie_details.*
import org.koin.android.viewmodel.ext.android.viewModel

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
            setImageSlider(movie.images.backdrops.map { imageInfo -> imageInfo.filePath }.take(6))

            movie_title.text = movie.title
            movie_genres.text = movie.genres.joinToString { it.name }
            movie_short_info.text =
                (movie.releaseDate + " | " + movie.originalLanguage + " | " + movie.runtime + "мин")
            expandable_text.text = movie.overview
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
}
