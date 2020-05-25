package com.majorik.moviebox.feature.search.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.search.domain.tmdbModels.movie.Movie
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.library.base.extensions.setIndicatorColor
import com.majorik.library.base.extensions.toDate
import com.majorik.library.base.utils.GenresStorageObject
import com.majorik.moviebox.feature.search.databinding.ItemCardWithDetailsBinding

internal class SearchMovieDetailedVH(val parent: ItemCardWithDetailsBinding) : RecyclerView.ViewHolder(parent.root) {
    @OptIn(ExperimentalStdlibApi::class)
    fun bindTo(item: Movie?) {
        item?.let {
            parent.cardImage.displayImageWithCenterCrop(UrlConstants.TMDB_BACKDROP_SIZE_1280 + it.backdropPath)
            parent.cardTitle.text = it.title

            if (!it.releaseDate.isNullOrEmpty()) {
                parent.cardReleaseDate.text = it.releaseDate.toDate().yearInt.toString()
            }

            parent.cardVoteAverage.text = it.voteAverage.toString()

            parent.cardGenres.text = it.genreIds.map { GenresStorageObject.movieGenres.get(it) }
                .joinToString(" - ") { (it ?: "").capitalize(AppConfig.APP_LOCALE) }

            parent.voteAverageIndicator.setIndicatorColor(item.voteAverage)
        }
    }
}
