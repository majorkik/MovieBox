package com.majorik.feature.details.presentation.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.feature.details.domain.tmdbModels.tv.TVDetails
import com.majorik.moviebox.R
import com.majorik.feature.details.presentation.adapters.SeasonAdapter.SeasonViewHolder
import com.majorik.feature.details.seasonDetails.SeasonDetailsActivity
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.library.base.extensions.setSafeOnClickListener
import kotlinx.android.synthetic.main.item_season_card.view.*

class SeasonAdapter(private val tvId: Int, private val seasons: List<TVDetails.Season>) :
    RecyclerView.Adapter<SeasonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(com.majorik.feature.details.R.layout.item_season_card, parent, false)

        return SeasonViewHolder(view)
    }

    override fun getItemCount() = seasons.size

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        holder.bindTo(seasons[position], tvId)
    }

    class SeasonViewHolder(private val parent: View) : RecyclerView.ViewHolder(parent) {
        fun bindTo(season: TVDetails.Season, tvId: Int) {
            itemView.season_name.text = season.name
            itemView.season_air_date.text = season.airDate
            itemView.season_count_episodes.text = ("${season.episodeCount} Серий")

            itemView.season_poster.displayImageWithCenterCrop(UrlConstants.TMDB_POSTER_SIZE_342 + season.posterPath)

            itemView.season_card.setSafeOnClickListener {
                val intent = Intent(parent.context, SeasonDetailsActivity::class.java)

                intent.putExtra("tv_id", tvId)
                intent.putExtra("season_number", season.seasonNumber)

                parent.context.startActivity(intent)
            }
        }
    }
}
