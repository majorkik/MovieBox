package com.majorik.moviebox.feature.search.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.search.domain.tmdbModels.tv.TV
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.library.base.extensions.setIndicatorColor
import com.majorik.library.base.extensions.toDate
import com.majorik.library.base.utils.GenresStorageObject
import com.majorik.moviebox.feature.search.databinding.ItemCardWithDetailsBinding

internal class SearchTVDetailedVH(val parent: ItemCardWithDetailsBinding) :
    RecyclerView.ViewHolder(parent.root) {
    @UseExperimental(ExperimentalStdlibApi::class)
    fun bindTo(item: TV?) {
        item?.let {
            parent.cardImage.displayImageWithCenterCrop(UrlConstants.TMDB_BACKDROP_SIZE_1280 + it.backdropPath)
            parent.cardTitle.text = it.name

            if (!it.firstAirDate.isNullOrEmpty()) {
                parent.cardReleaseDate.text = it.firstAirDate!!.toDate().yearInt.toString()
            }

            parent.cardVoteAverage.text = it.voteAverage.toString()

            parent.cardGenres.text = it.genreIds.map { GenresStorageObject.movieGenres.get(it) }
                .joinToString(" - ") { (it ?: "").capitalize(AppConfig.APP_LOCALE) }

            parent.voteAverageIndicator.setIndicatorColor(item.voteAverage)
        }
    }
}
