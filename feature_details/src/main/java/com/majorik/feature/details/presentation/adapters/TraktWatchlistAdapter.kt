package com.majorik.feature.details.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.feature.details.domain.traktModels.sync.TraktWatchlistResponse
import com.majorik.moviebox.R
import kotlinx.android.synthetic.main.item_poster_with_text.view.*

class TraktWatchlistAdapter(
    private val watchlistResponse: List<TraktWatchlistResponse>
) :
    RecyclerView.Adapter<TraktWatchlistAdapter.TraktWatchlistVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TraktWatchlistVH {
        val view = LayoutInflater.from(parent.context).inflate(com.majorik.feature.details.R.layout.item_poster_with_text, parent, false)

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
