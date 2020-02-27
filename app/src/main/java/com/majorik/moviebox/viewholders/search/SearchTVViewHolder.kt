package com.majorik.moviebox.viewholders.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.tv.TV
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.extensions.toDate
import com.majorik.moviebox.utils.GenresStorageObject
import kotlinx.android.synthetic.main.item_card_with_details.view.*

class SearchTVViewHolder(val parent: View) : RecyclerView.ViewHolder(parent) {
    fun bindTo(item: TV?) {
        item?.let {
            itemView.item_image.displayImageWithCenterCrop(UrlConstants.TMDB_POSTER_SIZE_185 + it.posterPath)
            itemView.title.text = it.name

            if(!it.firstAirDate.isNullOrEmpty()) {
                itemView.year.text = it.firstAirDate!!.toDate().yearInt.toString()
            }

            itemView.vote_average.text = it.voteAverage.toString()

            itemView.genres.text = it.genreIds.map { GenresStorageObject.movieGenres.get(it) }
                .joinToString(" - ") { (it ?: "").capitalize() }

            itemView.vote_count.text = it.voteCount.toString()
        }
    }
}
