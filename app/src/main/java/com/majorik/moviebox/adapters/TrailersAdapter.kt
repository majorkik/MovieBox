package com.majorik.moviebox.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.youtubeModels.SearchResponse
import com.majorik.moviebox.R
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.extensions.openYouTube
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.item_trailer_small_card.view.*

class TrailersAdapter(private val items: List<SearchResponse.Item>) :
    RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trailer_small_card, parent, false)

        return TrailerViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        holder.bindTo(items[position])
    }
    
    class TrailerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindTo(trailerItem: SearchResponse.Item) {
            itemView.trailer_name?.text = trailerItem.snippet.title
            itemView.trailer_image?.displayImageWithCenterCrop(trailerItem.snippet.thumbnails.high.url)

            itemView.trailer_image?.setOnClickListener {
                itemView.context.openYouTube(trailerItem.id.videoId)
            }
        }
    }
}
