package com.majorik.feature.navigation.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.feature.navigation.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.databinding.ItemTrendCardWithTitleBinding
import com.majorik.moviebox.databinding.ItemTrendLastItemCardBinding
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.startDetailsActivityWithId
import com.majorik.library.base.extensions.toDate
import com.majorik.library.base.utils.GenresStorageObject
import kotlin.math.roundToInt

class MovieTrendAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class TrendViewType {
        ITEM, LAST_ITEM
    }

    private val movies: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return if (viewType == TrendViewType.ITEM.ordinal) {
            val binding = ItemTrendCardWithTitleBinding.inflate(layoutInflater, parent, false)
            MovieTrendViewHolder(binding)
        } else {
            val binding = ItemTrendLastItemCardBinding.inflate(layoutInflater, parent, false)
            MovieTrendLastItemVH(binding)
        }

    }

    override fun getItemCount(): Int {
        return if(movies.size > 0){
            movies.size + 1
        }else{
            0
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < itemCount - 1) {
            TrendViewType.ITEM.ordinal
        } else {
            TrendViewType.LAST_ITEM.ordinal
        }
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieTrendViewHolder -> {
                holder.bindTo(movies[position])

                holder.itemView.setSafeOnClickListener {
                    holder.itemView.context.startDetailsActivityWithId(
                        movies[position].id,
                        "com.majorik.feature.details.presentation.movieDetails.MovieDetailsActivity"
                    )
                }
            }

            else -> {
                holder.itemView.setSafeOnClickListener {

                }
            }
        }
    }

    class MovieTrendViewHolder(private val parent: ItemTrendCardWithTitleBinding) :
        RecyclerView.ViewHolder(parent.root) {
        fun bindTo(movie: Movie) {
            parent.mTrendTitle.text = movie.title

            parent.mTrendYear.text = movie.releaseDate?.toDate()?.yearInt?.toString() ?: ""
            parent.mTrendGenres.text =
                movie.genreIds.map { GenresStorageObject.movieGenres.get(it) }
                    .take(2)
                    .joinToString(" - ") { it ?: "" }

            parent.mTrendRating.text = "${(movie.voteAverage * 10).roundToInt()}%"
        }
    }

    class MovieTrendLastItemVH(private val parent: ItemTrendLastItemCardBinding) :
        RecyclerView.ViewHolder(parent.root) {
    }
}
