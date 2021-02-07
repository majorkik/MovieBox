package com.majorik.moviebox.feature.details.presentation.seasonDetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majorik.library.base.extensions.setAdapterWithFixedSize
import com.majorik.moviebox.feature.details.R
import com.majorik.moviebox.feature.details.databinding.ActivitySeasonDetailsBinding
import com.majorik.moviebox.feature.details.presentation.adapters.EpisodeAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SeasonDetailsActivity : AppCompatActivity() {

    private val viewBinding: ActivitySeasonDetailsBinding by viewBinding()

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

        seasonDetailsViewModel.seasonDetailsLiveData.observe(
            this,
            {
                viewBinding.tvEpisodesList.setAdapterWithFixedSize(EpisodeAdapter(it.episodes))
            }
        )
    }
}
