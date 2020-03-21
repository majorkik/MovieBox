package com.majorik.moviebox.viewholders.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.tv.TV
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import kotlinx.android.synthetic.main.item_medium_poster_card.view.*

class SearchTVSmallVH(val parent: View) : RecyclerView.ViewHolder(parent) {
    @UseExperimental(ExperimentalStdlibApi::class)
    fun bindTo(item: TV?) {
        item?.let {
            itemView.collection_image.displayImageWithCenterCrop(UrlConstants.TMDB_POSTER_SIZE_185 + it.posterPath)
            itemView.title.text = it.name
        }
    }
}
