package com.majorik.moviebox.feature.search.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.search.domain.tmdbModels.movie.Movie
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.feature.search.databinding.ItemSearchMediumPosterCardBinding

internal class SearchMovieSmallVH(val viewBinding: ItemSearchMediumPosterCardBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bindTo(item: Movie?) {
        item?.let {
            viewBinding.collectionImage.displayImageWithCenterCrop(UrlConstants.TMDB_POSTER_SIZE_185 + it.posterPath)
            viewBinding.title.text = it.title
        }
    }
}
