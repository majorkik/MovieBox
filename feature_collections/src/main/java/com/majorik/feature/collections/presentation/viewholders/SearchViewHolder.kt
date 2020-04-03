package com.majorik.feature.collections.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.majorik.feature.collections.domain.tmdbModels.search.MultiSearchResponse
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.databinding.ItemCardWithDetailsBinding

class SearchViewHolder(val parent: ItemCardWithDetailsBinding) :
    RecyclerView.ViewHolder(parent.root) {
    fun bindTo(multiSearchItem: MultiSearchResponse.MultiSearchItem?) {
        multiSearchItem?.let {
            when (it.mediaType) {
                "movie" -> {
                    setData(
                        UrlConstants.TMDB_POSTER_SIZE_185 + it.posterPath,
                        it.title ?: "",
                        it.mediaType
                    )
                }
                "tv" -> {
                    setData(
                        UrlConstants.TMDB_POSTER_SIZE_185 + it.posterPath,
                        it.name ?: "",
                        it.mediaType
                    )
                }
                "person" -> {
                    setData(
                        UrlConstants.TMDB_POSTER_SIZE_185 + it.profilePath,
                        it.name ?: "",
                        ""
                    )
                }
            }
        }
    }

    private fun setData(url: String, title: String, year: String) {
        parent.cardImage.displayImageWithCenterCrop(url)
        parent.cardTitle.text = title
        parent.cardReleaseDate.text = year
    }
}
