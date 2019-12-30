package com.majorik.moviebox.ui.person

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.majorik.domain.constants.UrlConstants
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.MovieCreditsAdapter
import com.majorik.moviebox.adapters.TVCreditsAdapter
import com.majorik.moviebox.extensions.displayImageWithCenterInside
import com.majorik.moviebox.extensions.setAdapterWithFixedSize
import com.majorik.moviebox.extensions.setBlackAndWhite
import com.stfalcon.imageviewer.StfalconImageViewer
import kotlinx.android.synthetic.main.activity_person_details.*
import org.koin.android.viewmodel.ext.android.viewModel

class PersonDetailsActivity : AppCompatActivity() {

    private val personDetailsViewModel: PersonDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_details)

        setSupportActionBar(toolbar_person_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        fetchPersonDetails(intent.extras)
        setObservers()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun fetchPersonDetails(extras: Bundle?) {
        if (extras != null) {
            personDetailsViewModel.fetchPersonDetails(
                extras.getInt("id"),
                "ru-RU",
                "movie_credits,tv_credits,images,tagged_images"
            )
        }
    }

    private fun setObservers() {
        personDetailsViewModel.personDetailsLiveData.observe(this, Observer { personDetails ->
            person_profile_image.displayImageWithCenterInside(UrlConstants.TMDB_POSTER_SIZE_780 + personDetails.profilePath)
            person_profile_image.setOnClickListener {
                StfalconImageViewer.Builder<String>(
                    this,
                    personDetails.profileImages.profiles.map { it.filePath }
                ) { view, image ->
                    view.displayImageWithCenterInside(UrlConstants.TMDB_POSTER_SIZE_780 + image)
                }.show()
            }

            person_profile_image.setBlackAndWhite()
            person_name.text = personDetails.name
            expand_text_view.text = when {
                personDetails.biography.isEmpty() -> "Неизвестно"
                else -> personDetails.biography
            }
            person_birthday.text = personDetails.birthday
            person_place_of_birth.text = personDetails.placeOfBirth
            person_count_movies.text = personDetails.movieCredits.cast.size.toString()
            person_count_tvs.text = personDetails.tvCredits.cast.size.toString()
            person_count_episodes.text =
                personDetails.tvCredits.cast.sumBy { cast -> cast.episodeCount }.toString()

            person_movie_credits.setAdapterWithFixedSize(
                MovieCreditsAdapter(personDetails.movieCredits.cast), true
            )

            person_tv_credits.setAdapterWithFixedSize(
                TVCreditsAdapter(personDetails.tvCredits.cast),
                true
            )
        })
    }
}
