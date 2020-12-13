package com.majorik.moviebox.feature.search.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.search.domain.tmdbModels.tv.TV
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.feature.search.databinding.ItemSearchMediumPosterCardBinding

internal class SearchTVSmallVH(val viewBinding: ItemSearchMediumPosterCardBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bindTo(item: TV?) {
        item?.let {
            viewBinding.collectionImage.displayImageWithCenterCrop(UrlConstants.TMDB_POSTER_SIZE_185 + it.posterPath)
            viewBinding.title.text = it.name
        }
    }
}
