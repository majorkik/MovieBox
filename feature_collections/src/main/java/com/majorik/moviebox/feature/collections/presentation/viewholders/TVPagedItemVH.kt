package com.majorik.moviebox.feature.collections.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.collections.domain.tmdbModels.tv.TV
import com.majorik.library.base.constants.UrlConstants
import com.majorik.moviebox.databinding.ItemMediumPosterCardBinding
import com.majorik.library.base.extensions.displayImageWithCenterCrop

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
