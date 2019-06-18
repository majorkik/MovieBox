package com.majorik.moviebox.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.UrlConstants
import com.majorik.domain.models.person.PersonDetails
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.TVCreditsAdapter.TVCreditsViewHolder
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.ui.tvDetails.TVDetailsActivity
import kotlinx.android.synthetic.main.layout_item_card.view.*

class TVCreditsAdapter(private val tvCredits: List<PersonDetails.TVCredits.TVCast>) :
    RecyclerView.Adapter<TVCreditsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVCreditsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_card, parent, false)

        return TVCreditsViewHolder(view)
    }

    override fun getItemCount() = tvCredits.size

    override fun onBindViewHolder(holder: TVCreditsViewHolder, position: Int) {
        holder.bindTo(tvCredits[position])
    }


    class TVCreditsViewHolder(private val parent: View) : RecyclerView.ViewHolder(parent) {
        fun bindTo(cast: PersonDetails.TVCredits.TVCast) {
            itemView.collection_image.displayImageWithCenterCrop(UrlConstants.TMDB_POSTER_SIZE_185 + cast.posterPath)

            bindClickListener(cast)
        }

        private fun bindClickListener(cast: PersonDetails.TVCredits.TVCast) {
            itemView.collection_card.setOnClickListener {

                val intent = Intent(parent.context, TVDetailsActivity::class.java)

                intent.putExtra("id", cast.id)

                parent.context.startActivity(intent)
            }
        }
    }
}