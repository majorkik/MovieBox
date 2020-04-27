package com.majorik.moviebox.feature.details.presentation.adapters.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.details.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.feature.details.presentation.adapters.movie.MovieCollectionAdapter.MovieViewHolder
import com.majorik.moviebox.databinding.ItemSmallPosterCardBinding
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.startDetailsActivityWithId
import com.majorik.moviebox.feature.details.presentation.movieDetails.MovieDetailsActivity
import com.majorik.library.base.constants.UrlConstants
import kotlinx.android.synthetic.main.item_small_poster_card_details.view.*

class MovieCollectionAdapter : RecyclerView.Adapter<MovieViewHolder>() {

    private var movies: List<Movie> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            ItemSmallPosterCardBinding.inflate(layoutInflater, parent, false)

        return MovieViewHolder(binding)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindTo(movies[position])

        holder.itemView.collection_card.setSafeOnClickListener {
            holder.itemView.context.startDetailsActivityWithId(
                movies[position].id,
                MovieDetailsActivity::class.java
            )
        }
    }

    fun addItems(items: List<Movie>) {
        val startPosition = movies.size
        movies = items
        notifyItemRangeInserted(startPosition, items.size)
    }

    override fun getItemId(position: Int): Long {
        return movies[position].id.toLong()
    }

    class MovieViewHolder(private val parent: ItemSmallPosterCardBinding) :
        RecyclerView.ViewHolder(parent.root) {
        fun bindTo(movie: Movie) {
            parent.placeholderText.text = movie.title

            parent.collectionImage.displayImageWithCenterCrop(
                UrlConstants.TMDB_POSTER_SIZE_185 + movie.posterPath
            )
        }
    }
}