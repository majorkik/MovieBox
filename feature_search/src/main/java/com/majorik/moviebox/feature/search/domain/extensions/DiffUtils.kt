package com.majorik.moviebox.feature.search.domain.extensions

import androidx.recyclerview.widget.DiffUtil
import com.majorik.moviebox.feature.search.domain.models.discover.BaseDiscoverDomainModel
import com.majorik.moviebox.feature.search.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.feature.search.domain.tmdbModels.person.Person
import com.majorik.moviebox.feature.search.domain.tmdbModels.tv.TV

fun getMovieDiffUtils() = object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(
        oldItem: Movie,
        newItem: Movie
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: Movie,
        newItem: Movie
    ): Boolean = oldItem == newItem
}

fun getTVDiffUtils() = object : DiffUtil.ItemCallback<TV>() {
    override fun areItemsTheSame(
        oldItem: TV,
        newItem: TV
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: TV,
        newItem: TV
    ): Boolean = oldItem == newItem
}

fun getPersonDiffUtils() = object : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(
        oldItem: Person,
        newItem: Person
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: Person,
        newItem: Person
    ): Boolean = oldItem == newItem
}

fun getDiscoverDiffUtils() = object : DiffUtil.ItemCallback<BaseDiscoverDomainModel>() {
    override fun areItemsTheSame(
        oldItem: BaseDiscoverDomainModel,
        newItem: BaseDiscoverDomainModel
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: BaseDiscoverDomainModel,
        newItem: BaseDiscoverDomainModel
    ): Boolean = oldItem == newItem
}
