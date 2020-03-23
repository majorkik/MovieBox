package com.majorik.moviebox.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.tv.TV
import com.majorik.moviebox.databinding.ItemMediumPosterCardBinding
import com.majorik.moviebox.extensions.displayImageWithCenterCrop

class TVPagedItemVH(val parent: ItemMediumPosterCardBinding) :
    RecyclerView.ViewHolder(parent.root) {
    fun bindTo(tv: TV?) {
        tv?.let {
            parent.title.text = tv.name

            parent.collectionImage.displayImageWithCenterCrop(
                UrlConstants.TMDB_POSTER_SIZE_185 + it.posterPath
            )
        }
    }
}
