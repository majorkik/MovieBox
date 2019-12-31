package com.majorik.moviebox.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.tv.TV
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import kotlinx.android.synthetic.main.item_small_poster_card.view.*

class TVPagedItemVH(val parent: View) : RecyclerView.ViewHolder(parent) {
    fun bindTo(tv: TV?) {
        tv?.let {
            itemView.placeholder_text.text = tv.name

            itemView.collection_image.displayImageWithCenterCrop(
                UrlConstants.TMDB_POSTER_SIZE_185 + it.posterPath
            )
        }
    }
}