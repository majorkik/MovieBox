package com.majorik.feature.collections.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.feature.collections.domain.tmdbModels.tv.Episode
import com.majorik.library.base.constants.UrlConstants
import com.majorik.feature.collections.presentation.adapters.EpisodeAdapter.EpisodeViewHolder
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import kotlinx.android.synthetic.main.item_episode_card.view.*

class EpisodeAdapter(private val episodes: List<Episode>) :
    RecyclerView.Adapter<EpisodeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(com.majorik.feature.collections.R.layout.item_episode_card, parent, false)

        return EpisodeViewHolder(view)
    }

    override fun getItemCount() = episodes.size

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bindTo(episodes[position])
    }

    class EpisodeViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
        fun bindTo(episode: Episode) {
            itemView.episode_air_date.text = episode.airDate
            itemView.episode_overview.text = episode.overview
            itemView.episode_vote_average.text = ("${episode.voteAverage}/10")
            itemView.episode_number.text = ("Сезон ${episode.seasonNumber} | Серия ${episode.episodeNumber}")
            itemView.episode_name.text = episode.name
            itemView.episode_backdrop.displayImageWithCenterCrop(UrlConstants.TMDB_STILL_SIZE_300 + episode.stillPath)
        }
    }
}
