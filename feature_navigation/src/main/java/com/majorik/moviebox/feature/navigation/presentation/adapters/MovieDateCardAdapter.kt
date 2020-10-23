package com.majorik.moviebox.feature.navigation.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.movie.Movie
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.toDate
import com.majorik.moviebox.feature.navigation.databinding.ItemSmallPosterDateCardBinding
import com.majorik.moviebox.feature.navigation.domain.utils.getMovieDiffUtils
import com.soywiz.klock.KlockLocale
import com.soywiz.klock.locale.russian

class MovieDateCardAdapter(private val clickAction: (id: Int) -> Unit) :
    PagingDataAdapter<Movie, MovieDateCardAdapter.MovieViewHolder>(getMovieDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSmallPosterDateCardBinding.inflate(layoutInflater, parent, false)

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let {movie ->
            holder.bindTo(movie)

            holder.viewBinding.collectionCard.setSafeOnClickListener {
                clickAction(movie.id)
            }
        }
    }

    class MovieViewHolder(val viewBinding: ItemSmallPosterDateCardBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bindTo(movie: Movie) {
            val releaseDate = movie.releaseDate?.toDate()
            viewBinding.placeholderText.text = movie.title

            viewBinding.mReleaseDate.text =
                "${releaseDate?.dayOfMonth ?: ""} ${releaseDate?.month?.localShortName(KlockLocale.russian) ?: ""}"

            viewBinding.collectionImage.displayImageWithCenterCrop(
                UrlConstants.TMDB_POSTER_SIZE_185 + movie.posterPath
            )
        }
    }
}
