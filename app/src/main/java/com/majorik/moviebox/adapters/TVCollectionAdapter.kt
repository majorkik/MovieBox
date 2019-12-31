package com.majorik.moviebox.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.tv.TV
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.TVCollectionAdapter.*
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.extensions.startDetailsActivityWithId
import com.majorik.moviebox.ui.tvDetails.TVDetailsActivity
import kotlinx.android.synthetic.main.item_small_poster_card.view.*

class TVCollectionAdapter(private val movies: List<TV>) :
    RecyclerView.Adapter<CollectionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_small_poster_card, parent, false)

        return CollectionViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        holder.bindTo(movies[position])
    }

    class CollectionViewHolder(private val parent: View) : RecyclerView.ViewHolder(parent) {
        fun bindTo(tv: TV) {
            itemView.placeholder_text.text = tv.name

            itemView.collection_image.displayImageWithCenterCrop(UrlConstants.TMDB_POSTER_SIZE_185 + tv.posterPath)

            bindClickListener(tv)
        }

        private fun bindClickListener(tv: TV) {
            itemView.collection_card.setOnClickListener {
                parent.context.startDetailsActivityWithId(
                    tv.id,
                    TVDetailsActivity::class.java
                )
            }
        }
    }
}