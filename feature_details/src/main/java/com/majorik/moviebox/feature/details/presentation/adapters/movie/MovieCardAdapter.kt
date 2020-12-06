package com.majorik.moviebox.feature.details.presentation.adapters.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.library.base.constants.BaseIntentKeys.ITEM_ID
import com.majorik.moviebox.feature.details.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.feature.details.presentation.adapters.movie.MovieCardAdapter.*
import com.majorik.library.base.extensions.startActivityWithAnim
import com.majorik.moviebox.feature.details.presentation.movieDetails.MovieDetailsDialogFragment
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.moviebox.feature.details.databinding.ItemDetailsBigImageWithCornersBinding

class MovieCardAdapter : RecyclerView.Adapter<MovieCardViewHolder>() {

    private var movies: List<Movie> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemDetailsBigImageWithCornersBinding.inflate(layoutInflater, parent, false)

        return MovieCardViewHolder(binding)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieCardViewHolder, position: Int) {
        holder.bindTo(movies[position])

        holder.viewBinding.sliderLayout.setSafeOnClickListener {
            holder.itemView.context.startActivityWithAnim(
                MovieDetailsDialogFragment::class.java,
                Intent().apply {
                    putExtra(ITEM_ID, movies[position].id)
                }
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

    class MovieCardViewHolder(val viewBinding: ItemDetailsBigImageWithCornersBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bindTo(movie: Movie) {
            viewBinding.placeholderText.text = movie.title

            viewBinding.sliderImage.displayImageWithCenterCrop(
                UrlConstants.TMDB_BACKDROP_SIZE_1280 + movie.backdropPath
            )
        }
    }
}
