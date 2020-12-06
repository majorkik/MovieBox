package com.majorik.moviebox.feature.details.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.details.domain.tmdbModels.tv.Episode
import com.majorik.moviebox.feature.details.presentation.adapters.EpisodeAdapter.EpisodeViewHolder
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.feature.details.databinding.ItemEpisodeCardDetailsBinding

class EpisodeAdapter(private val episodes: List<Episode>) :
    RecyclerView.Adapter<EpisodeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemEpisodeCardDetailsBinding.inflate(layoutInflater, parent, false)

        return EpisodeViewHolder(viewBinding)
    }

    override fun getItemCount() = episodes.size

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bindTo(episodes[position])
    }

    class EpisodeViewHolder(val viewBinding: ItemEpisodeCardDetailsBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bindTo(episode: Episode) {
            viewBinding.episodeAirDate.text = episode.airDate
            viewBinding.episodeOverview.text = episode.overview
            viewBinding.episodeVoteAverage.text = ("${episode.voteAverage}/10")
            viewBinding.episodeNumber.text = ("Сезон ${episode.seasonNumber} | Серия ${episode.episodeNumber}")
            viewBinding.episodeName.text = episode.name
            viewBinding.episodeBackdrop.displayImageWithCenterCrop(UrlConstants.TMDB_STILL_SIZE_300 + episode.stillPath)
        }
    }
}
