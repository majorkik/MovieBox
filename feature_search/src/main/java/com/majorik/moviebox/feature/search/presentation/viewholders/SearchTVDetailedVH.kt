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

internal class SearchTVDetailedVH(val viewBinding: ItemCardWithDetailsBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bindTo(item: TV?) {
        item?.let {
            viewBinding.cardImage.displayImageWithCenterCrop(UrlConstants.TMDB_BACKDROP_SIZE_1280 + it.backdropPath)
            viewBinding.cardTitle.text = it.name

            if (!it.firstAirDate.isNullOrEmpty()) {
                viewBinding.cardReleaseDate.text = it.firstAirDate.toDate().yearInt.toString()
            }

            viewBinding.cardVoteAverage.text = it.voteAverage.toString()

            viewBinding.cardGenres.text = it.genreIds.map { GenresStorageObject.movieGenres.get(it) }
                .joinToString(" - ") { (it ?: "").capitalize(AppConfig.APP_LOCALE) }

            viewBinding.voteAverageIndicator.setIndicatorColor(item.voteAverage)
        }
    }
}
