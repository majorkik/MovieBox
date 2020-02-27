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
                        ""
                    )
                }
            }
        }
    }

    private fun setData(url: String, title: String, year: String) {
        itemView.item_image.displayImageWithCenterCrop(url)
        itemView.title.text = title
        itemView.year.text = year
    }
}
