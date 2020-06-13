package com.majorik.moviebox.feature.navigation.presentation.adapters.profile

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.majorik.library.base.constants.BaseIntentKeys
import com.majorik.library.base.constants.ScreenLinks
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.startActivityWithAnim
import com.majorik.moviebox.feature.navigation.databinding.ItemProfileCardBinding
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.movie.Movie

class ProfileMoviesAdapter : RecyclerView.Adapter<ProfileMoviesAdapter.ProfileMovieViewHolder>() {

    private var items: List<Movie> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileMovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemProfileCardBinding.inflate(layoutInflater, parent, false)
        return ProfileMovieViewHolder(binding)
    }

    override fun getItemCount() = items.count()

    override fun onBindViewHolder(holder: ProfileMovieViewHolder, position: Int) {
        holder.bindTo(items[position])

        holder.itemView.setSafeOnClickListener {
            holder.itemView.context.startActivityWithAnim(
                ScreenLinks.movieDetails,
                Intent().apply {
                    putExtra(BaseIntentKeys.ITEM_ID, items[position].id)
                }
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun addItems(movies: List<Movie>) {
        items = movies
        notifyItemRangeInserted(0, movies.count())
    }

    class ProfileMovieViewHolder(private val view: ItemProfileCardBinding) : RecyclerView.ViewHolder(view.root) {
        fun bindTo(movie: Movie) {
            view.imageView.load(UrlConstants.TMDB_POSTER_SIZE_185 + movie.posterPath)
        }
    }
}
