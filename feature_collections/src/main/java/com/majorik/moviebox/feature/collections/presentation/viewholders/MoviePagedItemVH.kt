package com.majorik.moviebox.feature.collections.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.collections.domain.tmdbModels.movie.Movie
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.feature.collections.databinding.ItemCollectionMediumPosterCardBinding

class MoviePagedItemVH(val parent: ItemCollectionMediumPosterCardBinding) :
    RecyclerView.ViewHolder(parent.root) {
    fun bindTo(movie: Movie?) {
        movie?.let {
            parent.title.text = movie.title

            parent.collectionImage.displayImageWithCenterCrop(
                UrlConstants.TMDB_POSTER_SIZE_185 + it.posterPath
            )
        }
    }
}
