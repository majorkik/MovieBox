package com.majorik.moviebox.feature.navigation.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.moviebox.feature.navigation.databinding.ItemSmallPosterCardBinding
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.feature.navigation.presentation.adapters.MovieCollectionAdapter.MovieViewHolder

class MovieCollectionAdapter(private val clickAction: (id: Int) -> Unit) : RecyclerView.Adapter<MovieViewHolder>() {

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

        holder.viewBinding.collectionCard.setSafeOnClickListener {
            clickAction(movies[position].id)
        }
    }

    override fun getItemId(position: Int): Long = movies[position].id.toLong()

    fun addItems(items: List<Movie>) {
        val startPosition = movies.size
        movies = items
        notifyItemRangeInserted(startPosition, items.size)
    }

    class MovieViewHolder(val viewBinding: ItemSmallPosterCardBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bindTo(movie: Movie) {
            viewBinding.placeholderText.text = movie.title

            viewBinding.collectionImage.displayImageWithCenterCrop(
                UrlConstants.TMDB_POSTER_SIZE_185 + movie.posterPath
            )
        }
    }
}
