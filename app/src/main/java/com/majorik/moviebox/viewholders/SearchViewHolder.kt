package com.majorik.moviebox.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.search.MultiSearchResponse
import com.majorik.moviebox.databinding.ItemCardWithDetailsBinding
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import kotlinx.android.synthetic.main.item_card_with_details.view.*

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
