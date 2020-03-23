package com.majorik.moviebox.adapters.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.databinding.ItemSmallPosterDateCardBinding
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.extensions.setSafeOnClickListener
import com.majorik.moviebox.extensions.startDetailsActivityWithId
import com.majorik.moviebox.extensions.toDate
import com.majorik.moviebox.ui.movieDetails.MovieDetailsActivity
import com.soywiz.klock.KlockLocale
import com.soywiz.klock.locale.russian
import kotlinx.android.synthetic.main.item_small_poster_date_card.view.*

class MovieDateCardAdapter() : RecyclerView.Adapter<MovieDateCardAdapter.MovieViewHolder>() {
    private var movies: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSmallPosterDateCardBinding.inflate(
            layoutInflater,
            parent,
            false
        )

        return MovieViewHolder(binding)
    }

    internal fun addItems(items: List<Movie>) {
        items.forEach {
            addItem(it)
        }
    }

    internal fun addItem(item: Movie) {
        val startedPosition = movies.size
        movies.add(item)
        notifyItemInserted(startedPosition)
    }

    override fun getItemCount() = movies.size

    override fun getItemId(position: Int): Long {
        return movies[position].id.toLong()
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindTo(movies[position])
    }

    class MovieViewHolder(private val parent: ItemSmallPosterDateCardBinding) :
        RecyclerView.ViewHolder(parent.root) {
        fun bindTo(movie: Movie) {
            val releaseDate = movie.releaseDate?.toDate()
            parent.placeholderText.text = movie.title

            parent.mReleaseDate.text =
                "${releaseDate?.dayOfMonth ?: ""} ${releaseDate?.month?.localShortName(KlockLocale.russian) ?: ""}"

            parent.collectionImage.displayImageWithCenterCrop(
                UrlConstants.TMDB_POSTER_SIZE_185 + movie.posterPath
            )

            bindClickListener(movie)
        }

        private fun bindClickListener(movie: Movie) {
            parent.collectionCard.setSafeOnClickListener {
                itemView.context.startDetailsActivityWithId(
                    movie.id,
                    MovieDetailsActivity::class.java
                )
            }
        }
    }
}
