package com.majorik.moviebox.viewholders.search

import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.constants.AppConfig
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.databinding.ItemCardWithDetailsBinding
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.extensions.setIndicatorColor
import com.majorik.moviebox.extensions.toDate
import com.majorik.moviebox.utils.GenresStorageObject
import kotlinx.android.synthetic.main.item_card_with_details.view.*

class SearchMovieDetailedVH(val parent: ItemCardWithDetailsBinding) : RecyclerView.ViewHolder(parent.root) {
    @UseExperimental(ExperimentalStdlibApi::class)
    fun bindTo(item: Movie?) {
        item?.let {
            parent.cardImage.displayImageWithCenterCrop(UrlConstants.TMDB_BACKDROP_SIZE_1280 + it.backdropPath)
            parent.cardTitle.text = it.title

            if (!it.releaseDate.isNullOrEmpty()) {
                parent.cardReleaseDate.text = it.releaseDate!!.toDate().yearInt.toString()
            }

            parent.cardVoteAverage.text = it.voteAverage.toString()

            parent.cardGenres.text = it.genreIds.map { GenresStorageObject.movieGenres.get(it) }
                .joinToString(" - ") { (it ?: "").capitalize(AppConfig.APP_LOCALE) }

            parent.voteAverageIndicator.setIndicatorColor(item.voteAverage)
        }
    }
}
