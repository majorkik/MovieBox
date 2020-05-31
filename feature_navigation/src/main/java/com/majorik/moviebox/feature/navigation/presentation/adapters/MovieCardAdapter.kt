package com.majorik.moviebox.feature.navigation.presentation.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.library.base.constants.BaseIntentKeys
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.startDetailsActivityWithId
import com.majorik.library.base.utils.PACKAGE_NAME
import com.majorik.moviebox.feature.navigation.databinding.ItemBigImageWithCornersBinding
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.feature.navigation.presentation.adapters.MovieCardAdapter.MovieCardViewHolder

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

            parent.sliderLayout.setSafeOnClickListener {
                parent.root.context.startDetailsActivityWithId(
                    "$PACKAGE_NAME.feature.details.presentation.movieDetails.MovieDetailsActivity",
                    Intent().apply {
                        putExtra(BaseIntentKeys.ITEM_ID, movie.id)
                    }
                )
            }
        }
    }
}
