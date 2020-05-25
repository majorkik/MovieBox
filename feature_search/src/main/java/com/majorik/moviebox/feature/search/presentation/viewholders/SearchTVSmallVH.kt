package com.majorik.moviebox.feature.search.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.search.domain.tmdbModels.tv.TV
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.feature.search.databinding.ItemSearchMediumPosterCardBinding

internal class SearchTVSmallVH(val parent: ItemSearchMediumPosterCardBinding) :
    RecyclerView.ViewHolder(parent.root) {
    @OptIn(ExperimentalStdlibApi::class)
    fun bindTo(item: TV?) {
        item?.let {
            parent.collectionImage.displayImageWithCenterCrop(UrlConstants.TMDB_POSTER_SIZE_185 + it.posterPath)
            parent.title.text = it.name
        }
    }
}
