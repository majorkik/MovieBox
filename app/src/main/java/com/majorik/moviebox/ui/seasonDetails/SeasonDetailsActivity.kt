package com.majorik.moviebox.ui.seasonDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.EpisodeAdapter
import com.majorik.moviebox.extensions.setAdapterWithFixedSize
import kotlinx.android.synthetic.main.activity_season_details.*
import org.koin.android.viewmodel.ext.android.viewModel

class SeasonDetailsActivity : AppCompatActivity() {

    private val seasonDetailsViewModel: SeasonDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_season_details)

        val extras = intent.extras
        if (extras != null) {
            seasonDetailsViewModel.fetchSeasonDetails(
                extras.getInt("tv_id"),
                extras.getInt("season_number"),
                "ru,en,null",
                ""
            )
        }

        seasonDetailsViewModel.seasonDetailsLiveData.observe(this, Observer {
            tv_episodes_list.setAdapterWithFixedSize(EpisodeAdapter(it.episodes))
        })
    }
}
