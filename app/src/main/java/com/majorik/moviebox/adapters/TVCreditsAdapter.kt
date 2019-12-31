package com.majorik.moviebox.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.cast.TVCast
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.TVCreditsAdapter.TVCreditsViewHolder
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.ui.tvDetails.TVDetailsActivity
import kotlinx.android.synthetic.main.item_small_poster_card.view.*

class TVCreditsAdapter(private val tvCredits: List<TVCast>) :
    RecyclerView.Adapter<TVCreditsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVCreditsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_small_poster_card, parent, false)

        return TVCreditsViewHolder(view)
    }

    override fun getItemCount() = tvCredits.size

    override fun onBindViewHolder(holder: TVCreditsViewHolder, position: Int) {
        holder.bindTo(tvCredits[position])
    }

    class TVCreditsViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
        fun bindTo(cast: TVCast) {
            itemView.placeholder_text.text = cast.name

            itemView.collection_image.displayImageWithCenterCrop(UrlConstants.TMDB_POSTER_SIZE_185 + cast.posterPath)

            bindClickListener(cast)
        }

        private fun bindClickListener(cast: TVCast) {
            itemView.collection_card.setOnClickListener {
                it.context.apply {
                    val intent = Intent(this, TVDetailsActivity::class.java)

                    intent.putExtra("id", cast.id)

                    startActivity(intent)
                }
            }
        }
    }
}