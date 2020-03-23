package com.majorik.moviebox.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.databinding.ItemMediumPosterCardBinding
import com.majorik.moviebox.extensions.displayImageWithCenterCrop

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
