package com.majorik.moviebox.feature.details.presentation.adapters.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.details.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.feature.details.presentation.adapters.movie.MovieCardAdapter.*
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.startDetailsActivityWithId
import com.majorik.moviebox.feature.details.presentation.movieDetails.MovieDetailsActivity
import com.majorik.library.base.constants.UrlConstants
import com.majorik.moviebox.feature.details.databinding.ItemBigImageWithCornersBinding
import kotlinx.android.synthetic.main.item_big_image_with_corners_details.view.*

class MovieCardAdapter : RecyclerView.Adapter<MovieCardViewHolder>() {

    private var movies: List<Movie> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBigImageWithCornersBinding.inflate(layoutInflater, parent, false)

        return MovieCardViewHolder(binding)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieCardViewHolder, position: Int) {
        holder.bindTo(movies[position])

        holder.itemView.slider_layout.setSafeOnClickListener {
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

    class MovieCardViewHolder(private val parent: ItemBigImageWithCornersBinding) :
        RecyclerView.ViewHolder(parent.root) {
        fun bindTo(movie: Movie) {
            parent.placeholderText.text = movie.title

            parent.sliderImage.displayImageWithCenterCrop(
                UrlConstants.TMDB_BACKDROP_SIZE_1280 + movie.backdropPath
            )
        }
    }
}
