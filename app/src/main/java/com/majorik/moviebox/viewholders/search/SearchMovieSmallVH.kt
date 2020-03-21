package com.majorik.moviebox.viewholders.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import kotlinx.android.synthetic.main.item_medium_poster_card.view.*

class SearchMovieSmallVH(val parent: View) : RecyclerView.ViewHolder(parent) {
    @UseExperimental(ExperimentalStdlibApi::class)
    fun bindTo(item: Movie?) {
        item?.let {
            parent.collection_image.displayImageWithCenterCrop(UrlConstants.TMDB_POSTER_SIZE_185 + it.posterPath)
            parent.title.text = it.title
        }
    }
}
