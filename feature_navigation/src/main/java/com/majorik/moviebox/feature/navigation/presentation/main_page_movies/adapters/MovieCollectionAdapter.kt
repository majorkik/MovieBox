package com.majorik.moviebox.feature.navigation.presentation.main_page_movies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.moviebox.feature.navigation.databinding.ItemSmallPosterCardBinding
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.feature.navigation.domain.utils.getMovieDiffUtils
import com.majorik.moviebox.feature.navigation.presentation.main_page_movies.adapters.MovieCollectionAdapter.MovieViewHolder

class MovieCollectionAdapter(private val clickAction: (id: Int) -> Unit) :
    PagingDataAdapter<Movie, MovieViewHolder>(getMovieDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            ItemSmallPosterCardBinding.inflate(layoutInflater, parent, false)

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { movie ->
            holder.bindTo(movie)

            holder.viewBinding.collectionCard.setSafeOnClickListener {
                clickAction(movie.id)
            }
        }
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
