package com.majorik.feature.navigation.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.feature.navigation.databinding.ItemTrailerSmallCardBinding
import com.majorik.feature.navigation.domain.youtubeModels.SearchResponse
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.library.base.extensions.openYouTube
import com.majorik.library.base.extensions.setSafeOnClickListener
import kotlinx.android.synthetic.main.item_trailer_small_card.view.*

class TrailersAdapter() : RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder>() {

    private var items: List<SearchResponse.Item> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTrailerSmallCardBinding.inflate(layoutInflater, parent, false)

        return TrailerViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun getItemId(position: Int): Long {
        return items[position].id.hashCode().toLong()
    }

    fun addItems(items: List<SearchResponse.Item>) {
        val startPosition = items.size
        this.items = items
        notifyItemRangeInserted(startPosition, items.size)
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        holder.bindTo(items[position])

        holder.itemView.trailer_image?.setSafeOnClickListener {
            holder.itemView.context.openYouTube(items[position].id.videoId)
        }
    }

    class TrailerViewHolder(private val view: ItemTrailerSmallCardBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bindTo(trailerItem: SearchResponse.Item) {
            view.trailerName.text = trailerItem.snippet.title
            view.trailerImage.displayImageWithCenterCrop(trailerItem.snippet.thumbnails.high.url)
        }
    }
}
