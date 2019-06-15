package com.majorik.moviebox.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.majorik.domain.UrlConstants
import com.majorik.domain.models.tv.TVResponse
import com.majorik.moviebox.GlideApp
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.TVCollectionAdapter.*
import com.majorik.moviebox.ui.tvDetails.TVDetailsActivity
import kotlinx.android.synthetic.main.layout_item_card.view.*

class TVCollectionAdapter(private val movies: List<TVResponse.TV>) :
    RecyclerView.Adapter<CollectionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_card, parent, false)

        return CollectionViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        holder.bindTo(movies[position])
    }

    class CollectionViewHolder(private val parent: View) : RecyclerView.ViewHolder(parent) {
        fun bindTo(tv: TVResponse.TV) {
            GlideApp.with(itemView.collection_image)
                .load(UrlConstants.TMDB_POSTER_SIZE_185 + tv.posterPath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(itemView.collection_image)

            bindClickListener(tv)
        }

        private fun bindClickListener(tv: TVResponse.TV) {
            itemView.collection_card.setOnClickListener {

                val intent = Intent(parent.context, TVDetailsActivity::class.java)

                intent.putExtra("id", tv.id)

                parent.context.startActivity(intent)
            }
        }
    }
}