package com.majorik.moviebox.feature.navigation.domain.utils

import androidx.recyclerview.widget.DiffUtil
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.person.Person
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.tv.TV

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

fun getPersonDiffUtils() = object : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(
        oldItem: Person,
        newItem: Person
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Person,
        newItem: Person
    ): Boolean = oldItem.id == newItem.id
}
