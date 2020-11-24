package com.majorik.moviebox.feature.collections.presentation.movieTabCollections.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.feature.collections.domain.tmdbModels.movie.Movie
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.moviebox.feature.collections.databinding.ItemCollectionMediumPosterCardBinding
import com.majorik.moviebox.feature.collections.domain.utils.getMovieDiffUtils
import kotlinx.android.synthetic.main.item_collection_medium_poster_card.view.*

class PagingMovieCollectionAdapter(private val actionClick: (id: Int) -> Unit) :
    PagingDataAdapter<Movie, PagingMovieCollectionAdapter.MoviePagedViewHolder>(getMovieDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePagedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemCollectionMediumPosterCardBinding.inflate(layoutInflater, parent, false)

        return MoviePagedViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviePagedViewHolder, position: Int) {
        getItem(position)?.let { movie ->
            holder.bindTo(movie)

            holder.itemView.collection_card.setSafeOnClickListener {
                actionClick(movie.id)
            }
        }
    }

    class MoviePagedViewHolder(val viewBinding: ItemCollectionMediumPosterCardBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bindTo(movie: Movie) {
            viewBinding.title.text = movie.title

            viewBinding.collectionImage.displayImageWithCenterCrop(
                UrlConstants.TMDB_POSTER_SIZE_185 + movie.posterPath
            )
        }
    }
}
