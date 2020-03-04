package com.majorik.moviebox.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.R
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.extensions.setSafeOnClickListener
import com.majorik.moviebox.extensions.startDetailsActivityWithId
import com.majorik.moviebox.extensions.toDate
import com.majorik.moviebox.ui.movieDetails.MovieDetailsActivity
import com.soywiz.klock.KlockLocale
import com.soywiz.klock.locale.russian
import kotlinx.android.synthetic.main.item_small_poster_date_card.view.*

class MovieDateCardAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieDateCardAdapter.MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_small_poster_date_card, parent, false)

        return MovieViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindTo(movies[position])
    }

    class MovieViewHolder(private val parent: View) : RecyclerView.ViewHolder(parent) {
        fun bindTo(movie: Movie) {
            val releaseDate = movie.releaseDate?.toDate()
            itemView.placeholder_text.text = movie.title

            itemView.m_release_date.text =
                "${releaseDate?.dayOfMonth ?: ""} ${releaseDate?.month?.localShortName(KlockLocale.russian) ?: ""}"

            itemView.collection_image.displayImageWithCenterCrop(
                UrlConstants.TMDB_POSTER_SIZE_185 + movie.posterPath
            )

            bindClickListener(movie)
        }

        private fun bindClickListener(movie: Movie) {
            itemView.collection_card.setSafeOnClickListener {
                parent.context.startDetailsActivityWithId(
                    movie.id,
                    MovieDetailsActivity::class.java
                )
            }
        }
    }
}
