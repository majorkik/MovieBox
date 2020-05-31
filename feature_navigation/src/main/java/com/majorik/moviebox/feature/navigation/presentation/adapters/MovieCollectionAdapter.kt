package com.majorik.moviebox.feature.navigation.presentation.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.library.base.constants.BaseIntentKeys
import com.majorik.library.base.constants.ScreenLinks
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.startDetailsActivityWithId
import com.majorik.moviebox.feature.navigation.databinding.ItemSmallPosterCardBinding
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.feature.navigation.presentation.adapters.MovieCollectionAdapter.MovieViewHolder

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

            parent.collectionCard.setSafeOnClickListener {
                parent.root.context.startDetailsActivityWithId(
                    ScreenLinks.movieDetails,
                    Intent().apply {
                        putExtra(BaseIntentKeys.ITEM_ID, movie.id)
                    }
                )
            }
        }
    }
}
