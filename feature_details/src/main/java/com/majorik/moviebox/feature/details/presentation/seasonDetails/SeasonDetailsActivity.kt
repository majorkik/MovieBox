package com.majorik.moviebox.feature.details.presentation.seasonDetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.majorik.moviebox.feature.details.presentation.adapters.EpisodeAdapter
import com.majorik.library.base.extensions.setAdapterWithFixedSize
import kotlinx.android.synthetic.main.activity_season_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SeasonDetailsActivity : AppCompatActivity() {

    private val seasonDetailsViewModel: SeasonDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.majorik.moviebox.feature.details.R.layout.activity_season_details)

        val extras = intent.extras
        if (extras != null) {
            seasonDetailsViewModel.fetchSeasonDetails(
                extras.getInt("tv_id"),
                extras.getInt("season_number"),
                "ru,en,null",
                ""
            )
        }

        seasonDetailsViewModel.seasonDetailsLiveData.observe(
            this,
            Observer {
                tv_episodes_list.setAdapterWithFixedSize(EpisodeAdapter(it.episodes))
            }
        )
    }
}
