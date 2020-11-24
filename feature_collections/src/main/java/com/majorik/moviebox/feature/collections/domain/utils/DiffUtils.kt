package com.majorik.moviebox.feature.collections.domain.utils

import androidx.recyclerview.widget.DiffUtil
import com.majorik.moviebox.feature.collections.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.feature.collections.domain.tmdbModels.tv.TV

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

fun getTVDiffUtils() = object : DiffUtil.ItemCallback<TV>() {
    override fun areItemsTheSame(
        oldItem: TV,
        newItem: TV
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: TV,
        newItem: TV
    ): Boolean = oldItem.id == newItem.id
}
