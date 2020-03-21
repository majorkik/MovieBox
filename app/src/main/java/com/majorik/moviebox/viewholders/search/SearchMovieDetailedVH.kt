package com.majorik.moviebox.viewholders.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.constants.AppConfig
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.extensions.setIndicatorColor
import com.majorik.moviebox.extensions.toDate
import com.majorik.moviebox.utils.GenresStorageObject
import kotlinx.android.synthetic.main.item_card_with_details.view.*

class SearchMovieDetailedVH(val parent: View) : RecyclerView.ViewHolder(parent) {
    @UseExperimental(ExperimentalStdlibApi::class)
    fun bindTo(item: Movie?) {
        item?.let {
            itemView.card_image.displayImageWithCenterCrop(UrlConstants.TMDB_BACKDROP_SIZE_1280 + it.backdropPath)
            itemView.card_title.text = it.title

            if (!it.releaseDate.isNullOrEmpty()) {
                parent.card_release_date.text = it.releaseDate!!.toDate().yearInt.toString()
            }

            itemView.card_vote_average.text = it.voteAverage.toString()

            itemView.card_genres.text = it.genreIds.map { GenresStorageObject.movieGenres.get(it) }
                .joinToString(" - ") { (it ?: "").capitalize(AppConfig.APP_LOCALE) }

            itemView.vote_average_indicator.setIndicatorColor(item.voteAverage)
        }
    }
}
