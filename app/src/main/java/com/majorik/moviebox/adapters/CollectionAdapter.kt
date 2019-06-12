package com.majorik.moviebox.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.majorik.domain.models.CollectionResponse
import com.majorik.moviebox.GlideApp
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.CollectionAdapter.*
import kotlinx.android.synthetic.main.layout_item_card.view.*

class CollectionAdapter(private val collectionItems: List<CollectionResponse.CollectionItem>) :
    RecyclerView.Adapter<CollectionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_card, parent, false)

        return CollectionViewHolder(view)
    }

    override fun getItemCount() = collectionItems.size

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        holder.bindTo(collectionItems[position])
    }

    class CollectionViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
        fun bindTo(collectionItem: CollectionResponse.CollectionItem) {
            GlideApp.with(itemView.collection_image)
                .load(UrlConstants.TMDB_POSTER_SIZE_185 + collectionItem.posterPath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(itemView.collection_image)
        }
    }
}