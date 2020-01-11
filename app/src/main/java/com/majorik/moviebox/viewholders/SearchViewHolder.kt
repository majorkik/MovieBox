package com.majorik.moviebox.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.search.MultiSearchResponse
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import kotlinx.android.synthetic.main.item_card_with_details.view.*

class SearchViewHolder(val parent: View) : RecyclerView.ViewHolder(parent) {
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
                        it.mediaType
                    )
                }
            }
        }
    }

    private fun setData(url: String, title: String, mediaType: String) {
        itemView.multisearch_image.displayImageWithCenterCrop(url)
        itemView.multisearch_title.text = title
        itemView.multisearch_type.text = when (mediaType) {
            "movie" -> "Фильм"
            "tv" -> "Сериал"
            "person" -> "Актёр"
            else -> ""
        }
    }
}
