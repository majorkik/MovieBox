package com.majorik.moviebox.feature.navigation.presentation.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.library.base.constants.BaseIntentKeys
import com.majorik.library.base.constants.ScreenLinks
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.movie.Movie
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.startActivityWithAnim
import com.majorik.library.base.extensions.toDate
import com.majorik.moviebox.feature.navigation.databinding.ItemSmallPosterDateCardBinding
import com.soywiz.klock.KlockLocale
import com.soywiz.klock.locale.russian

class MovieDateCardAdapter(private val clickAction: (id: Int) -> Unit) :
    RecyclerView.Adapter<MovieDateCardAdapter.MovieViewHolder>() {
    private var movies: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSmallPosterDateCardBinding.inflate(
            layoutInflater,
            parent,
            false
        )

        return MovieViewHolder(
            binding
        )
    }

    fun addItems(items: List<Movie>) {
        items.forEach {
            addItem(it)
        }
    }

    private fun addItem(item: Movie) {
        val startedPosition = movies.size
        movies.add(item)
        notifyItemInserted(startedPosition)
    }

    override fun getItemCount() = movies.size

    override fun getItemId(position: Int): Long {
        return movies[position].id.toLong()
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindTo(movies[position])

        holder.viewBinding.collectionCard.setSafeOnClickListener {
            clickAction(movies[position].id)
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
