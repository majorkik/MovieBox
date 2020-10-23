package com.majorik.moviebox.feature.navigation.presentation.main_page_movies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.moviebox.feature.navigation.databinding.ItemBigImageWithCornersBinding
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.feature.navigation.domain.utils.getMovieDiffUtils
import com.majorik.moviebox.feature.navigation.presentation.main_page_movies.adapters.MovieCardAdapter.MovieCardViewHolder

class MovieCardAdapter(private val clickAction: (id: Int) -> Unit) : PagingDataAdapter<Movie, MovieCardViewHolder>(
    getMovieDiffUtils()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBigImageWithCornersBinding.inflate(layoutInflater, parent, false)

        return MovieCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieCardViewHolder, position: Int) {
        getItem(position)?.let { movie ->
            holder.bindTo(movie)

            holder.viewBinding.sliderLayout.setSafeOnClickListener {
                clickAction(movie.id)
            }
        }
    }

    class MovieCardViewHolder(val viewBinding: ItemBigImageWithCornersBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bindTo(movie: Movie) {
            viewBinding.placeholderText.text = movie.title

            viewBinding.sliderImage.displayImageWithCenterCrop(
                UrlConstants.TMDB_BACKDROP_SIZE_1280 + movie.backdropPath
            )
        }
    }
}
