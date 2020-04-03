package com.majorik.feature.details.presentation

import androidx.recyclerview.widget.RecyclerView
import com.majorik.feature.details.domain.tmdbModels.movie.Movie
import com.majorik.library.base.constants.UrlConstants
import com.majorik.moviebox.databinding.ItemMediumPosterCardBinding
import com.majorik.library.base.extensions.displayImageWithCenterCrop

class MoviePagedItemVH(val parent: ItemMediumPosterCardBinding) :
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
