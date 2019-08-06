package com.majorik.moviebox.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.UrlConstants
import com.majorik.domain.tmdbModels.tv.TVDetails
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.SeasonAdapter.SeasonViewHolder
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.ui.seasonDetails.SeasonDetailsActivity
import kotlinx.android.synthetic.main.item_season_card.view.*

class SeasonAdapter(private val tvId: Int, private val seasons: List<TVDetails.Season>) :
    RecyclerView.Adapter<SeasonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_season_card, parent, false)

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

            itemView.season_card.setOnClickListener {
                val intent = Intent(parent.context, SeasonDetailsActivity::class.java)

                intent.putExtra("tv_id", tvId)
                intent.putExtra("season_number", season.seasonNumber)

                parent.context.startActivity(intent)
            }
        }
    }
}