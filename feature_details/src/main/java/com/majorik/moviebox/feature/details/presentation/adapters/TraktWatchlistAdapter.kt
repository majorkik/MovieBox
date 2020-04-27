package com.majorik.moviebox.feature.details.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.details.domain.traktModels.sync.TraktWatchlistResponse
import kotlinx.android.synthetic.main.item_poster_with_text_details.view.*

class TraktWatchlistAdapter(
    private val watchlistResponse: List<TraktWatchlistResponse>
) :
    RecyclerView.Adapter<TraktWatchlistAdapter.TraktWatchlistVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TraktWatchlistVH {
        val view = LayoutInflater.from(parent.context).inflate(com.majorik.moviebox.feature.details.R.layout.item_poster_with_text_details, parent, false)

        return TraktWatchlistVH(view)
    }

    override fun getItemCount() = watchlistResponse.size

    override fun onBindViewHolder(holder: TraktWatchlistVH, position: Int) {
        holder.bindTo(watchlistResponse[position])
    }

    class TraktWatchlistVH(private val parent: View) :
        RecyclerView.ViewHolder(parent) {
        fun bindTo(watchlistResponse: TraktWatchlistResponse) {
            itemView.poster_title.text =
                    watchlistResponse.movie?.title
        }
    }
}
