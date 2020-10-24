package com.majorik.moviebox.feature.navigation.presentation.main_page_profile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.moviebox.feature.navigation.databinding.ItemProfileCardBinding
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.feature.navigation.domain.utils.getMovieDiffUtils

class ProfileMoviesAdapter(private val actionClick: (id: Int) -> Unit) :
    PagingDataAdapter<Movie, ProfileMoviesAdapter.ProfileMovieViewHolder>(getMovieDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileMovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProfileCardBinding.inflate(layoutInflater, parent, false)

        return ProfileMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileMovieViewHolder, position: Int) {
        getItem(position)?.let { movie ->
            holder.bindTo(movie)

            holder.viewBinding.root.setSafeOnClickListener {
                actionClick(movie.id)
            }
        }
    }

    class ProfileMovieViewHolder(val viewBinding: ItemProfileCardBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bindTo(movie: Movie) {
            viewBinding.imageView.load(UrlConstants.TMDB_POSTER_SIZE_185 + movie.posterPath)
        }
    }
}
