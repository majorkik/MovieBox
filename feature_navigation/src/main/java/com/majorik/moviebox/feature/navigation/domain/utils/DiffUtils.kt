package com.majorik.moviebox.feature.navigation.domain.utils

import androidx.recyclerview.widget.DiffUtil
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.movie.Movie

fun getMovieDiffUtils() = object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(
        oldItem: Movie,
        newItem: Movie
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Movie,
        newItem: Movie
    ): Boolean = oldItem.id == newItem.id
}
