package com.majorik.moviebox.viewholders

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.UrlConstants
import com.majorik.domain.models.tv.TVResponse
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import kotlinx.android.synthetic.main.item_small_poster_card.view.*

class TVPagedItemVH(val parent: View) : RecyclerView.ViewHolder(parent) {
    fun bindTo(movie: TVResponse.TV?) {
        movie?.let {
            if (it.posterPath != null) {
                itemView.collection_image.displayImageWithCenterCrop(
                    UrlConstants.TMDB_POSTER_SIZE_185 + it.posterPath
                )
            }
        }
    }
}