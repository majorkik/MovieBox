package com.majorik.moviebox.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.majorik.domain.UrlConstants
import com.majorik.domain.tmdbModels.movie.MovieResponse
import com.majorik.moviebox.GlideApp
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.MovieCollectionAdapter.*
import com.majorik.moviebox.ui.movieDetails.MovieDetailsActivity
import kotlinx.android.synthetic.main.item_small_poster_card.view.*

class MovieCollectionAdapter(private val movies: List<MovieResponse.Movie>) :
    RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_small_poster_card, parent, false)

        return MovieViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindTo(movies[position])
    }

    class MovieViewHolder(private val parent: View) : RecyclerView.ViewHolder(parent) {
        fun bindTo(movie: MovieResponse.Movie) {
            GlideApp.with(itemView.collection_image)
                .load(UrlConstants.TMDB_POSTER_SIZE_185 + movie.posterPath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(itemView.collection_image)

            bindClickListener(movie)
        }

        private fun bindClickListener(movie: MovieResponse.Movie) {
            itemView.collection_card.setOnClickListener {
                val intent = Intent(parent.context, MovieDetailsActivity::class.java)

                intent.putExtra("id", movie.id)

                parent.context.startActivity(intent)
            }
        }
    }
}