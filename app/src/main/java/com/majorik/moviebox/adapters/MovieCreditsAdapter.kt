package com.majorik.moviebox.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.UrlConstants
import com.majorik.domain.models.person.PersonDetails
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.MovieCreditsAdapter.MovieCreditsViewHolder
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.ui.movieDetails.MovieDetailsActivity
import kotlinx.android.synthetic.main.item_small_poster_card.view.*

class MovieCreditsAdapter(private val movieCredits: List<PersonDetails.MovieCredits.MovieCast>) :
    RecyclerView.Adapter<MovieCreditsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCreditsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_small_poster_card, parent, false)

        return MovieCreditsViewHolder(view)
    }

    override fun getItemCount() = movieCredits.size

    override fun onBindViewHolder(holder: MovieCreditsViewHolder, position: Int) {
        holder.bindTo(movieCredits[position])
    }

    class MovieCreditsViewHolder(private val parent: View) : RecyclerView.ViewHolder(parent) {
        fun bindTo(cast: PersonDetails.MovieCredits.MovieCast) {
            itemView.collection_image.displayImageWithCenterCrop(UrlConstants.TMDB_POSTER_SIZE_185 + cast.posterPath)

            bindClickListener(cast)
        }

        private fun bindClickListener(cast: PersonDetails.MovieCredits.MovieCast) {
            itemView.collection_card.setOnClickListener {
                val intent = Intent(parent.context, MovieDetailsActivity::class.java)

                intent.putExtra("id", cast.id)

                parent.context.startActivity(intent)
            }
        }
    }
}