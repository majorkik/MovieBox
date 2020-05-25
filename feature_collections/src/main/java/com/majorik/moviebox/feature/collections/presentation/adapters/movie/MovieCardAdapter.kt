package com.majorik.moviebox.feature.collections.presentation.adapters.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.library.base.constants.ScreenLinks
import com.majorik.moviebox.feature.collections.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.feature.collections.presentation.adapters.movie.MovieCardAdapter.*
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.startDetailsActivityWithId
import com.majorik.moviebox.feature.collections.databinding.ItemCollectionBigImageWithCornersBinding
import kotlinx.android.synthetic.main.item_collection_big_image_with_corners.view.*

class MovieCardAdapter : RecyclerView.Adapter<MovieCardViewHolder>() {

    private var movies: List<Movie> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCollectionBigImageWithCornersBinding.inflate(layoutInflater, parent, false)

        return MovieCardViewHolder(binding)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieCardViewHolder, position: Int) {
        holder.bindTo(movies[position])

        holder.itemView.slider_layout.setSafeOnClickListener {
            holder.itemView.context.startDetailsActivityWithId(
                movies[position].id,
                ScreenLinks.movieDetails
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

    class MovieCardViewHolder(private val parent: ItemCollectionBigImageWithCornersBinding) :
        RecyclerView.ViewHolder(parent.root) {
        fun bindTo(movie: Movie) {
            parent.placeholderText.text = movie.title

            parent.sliderImage.displayImageWithCenterCrop(
                UrlConstants.TMDB_BACKDROP_SIZE_1280 + movie.backdropPath
            )
        }
    }
}
