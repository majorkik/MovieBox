package com.majorik.moviebox.feature.search.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.search.domain.tmdbModels.movie.Movie
import com.majorik.library.base.constants.UrlConstants
import com.majorik.moviebox.databinding.ItemMediumPosterCardBinding
import com.majorik.library.base.extensions.displayImageWithCenterCrop

class SearchMovieSmallVH(val parent: ItemMediumPosterCardBinding) :
    RecyclerView.ViewHolder(parent.root) {
    @UseExperimental(ExperimentalStdlibApi::class)
    fun bindTo(item: Movie?) {
        item?.let {
            parent.collectionImage.displayImageWithCenterCrop(UrlConstants.TMDB_POSTER_SIZE_185 + it.posterPath)
            parent.title.text = it.title
        }
    }
}