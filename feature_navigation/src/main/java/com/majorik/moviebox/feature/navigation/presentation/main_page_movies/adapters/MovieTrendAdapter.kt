package com.majorik.moviebox.feature.navigation.presentation.main_page_movies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.toDate
import com.majorik.library.base.utils.GenresStorageObject
import com.majorik.moviebox.feature.navigation.databinding.ItemTrendCardWithTitleBinding
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.feature.navigation.domain.utils.getMovieDiffUtils
import com.majorik.moviebox.feature.navigation.presentation.main_page_movies.adapters.MovieTrendAdapter.MovieTrendViewHolder
import kotlin.math.roundToInt

class MovieTrendAdapter(private val clickAction: (id: Int) -> Unit) :
    PagingDataAdapter<Movie, MovieTrendViewHolder>(getMovieDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTrendViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTrendCardWithTitleBinding.inflate(layoutInflater, parent, false)

        return MovieTrendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieTrendViewHolder, position: Int) {
        getItem(position)?.let { movie ->
            holder.bindTo(movie)

            holder.viewBinding.root.setSafeOnClickListener {
                clickAction(movie.id)
            }
        }
    }

    class MovieTrendViewHolder(val viewBinding: ItemTrendCardWithTitleBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bindTo(movie: Movie) {
            viewBinding.mTrendTitle.text = movie.title

            viewBinding.mTrendYear.text = movie.releaseDate?.toDate()?.yearInt?.toString() ?: ""
            viewBinding.mTrendGenres.text =
                movie.genreIds.map { GenresStorageObject.movieGenres.get(it) }
                    .take(2)
                    .joinToString(" - ") { it ?: "" }

            viewBinding.mTrendRating.text = "${(movie.voteAverage * 10).roundToInt()}%"
        }
    }
}
