package com.majorik.moviebox.feature.navigation.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.movie.Movie
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.startDetailsActivityWithId
import com.majorik.library.base.extensions.toDate
import com.majorik.library.base.utils.PACKAGE_NAME
import com.majorik.moviebox.feature.navigation.databinding.ItemSmallPosterDateCardBinding
import com.soywiz.klock.KlockLocale
import com.soywiz.klock.locale.russian

class MovieDateCardAdapter : RecyclerView.Adapter<MovieDateCardAdapter.MovieViewHolder>() {
    private var movies: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSmallPosterDateCardBinding.inflate(
            layoutInflater,
            parent,
            false
        )

        return MovieViewHolder(
            binding
        )
    }

    fun addItems(items: List<Movie>) {
        items.forEach {
            addItem(it)
        }
    }

    private fun addItem(item: Movie) {
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
                parent.root.context.startDetailsActivityWithId(
                    movie.id,
                    "$PACKAGE_NAME.feature.details.presentation.movieDetails.MovieDetailsActivity"
                )
            }
        }
    }
}
