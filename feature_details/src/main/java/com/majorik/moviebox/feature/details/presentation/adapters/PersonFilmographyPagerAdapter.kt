package com.majorik.moviebox.feature.details.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.details.domain.tmdbModels.cast.MovieCast
import com.majorik.moviebox.feature.details.R
import com.majorik.moviebox.feature.details.domain.tmdbModels.cast.TVCast
import com.majorik.moviebox.feature.details.presentation.adapters.PersonFilmographyPagerAdapter.PageViewHolder
import com.majorik.moviebox.feature.details.presentation.adapters.movie.MovieCreditsAdapter
import com.majorik.moviebox.feature.details.presentation.adapters.tv.TVCreditsAdapter
import com.majorik.library.base.extensions.toPx
import com.majorik.library.base.utils.SpacingDecoration
import kotlinx.android.synthetic.main.item_filmography_details.view.*

class PersonFilmographyPagerAdapter(
    private val movieCasts: List<MovieCast>,
    private val tvCast: List<TVCast>
) : RecyclerView.Adapter<PageViewHolder>() {
    private var spanCount = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_filmography_details, parent, false)

        return PageViewHolder(view)
    }

    override fun getItemCount() = 2

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        when (position) {
            0 -> {
                holder.bindMovies(spanCount, movieCasts)
            }

            else -> {
                holder.bindTVs(spanCount, tvCast)
            }
        }
    }

    fun changeViewType(isGrid: Boolean) {
        if (isGrid) {
            spanCount = 3
        } else {
            spanCount = 1
        }

//        notifyItemRangeChanged(0, 2)
        notifyDataSetChanged()
    }

    class PageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val itemDecoration = SpacingDecoration(16.toPx(), 16.toPx(), true)

        fun bindMovies(
            spanCount: Int,
            movieCasts: List<MovieCast>
        ) {
            val layoutManager = (itemView.filmographylist.layoutManager as? GridLayoutManager)
            layoutManager?.spanCount = spanCount

            if (layoutManager?.spanCount == 3) {
                itemView.filmographylist.removeItemDecoration(itemDecoration)
                itemView.filmographylist.addItemDecoration(itemDecoration)
            } else {
                itemView.filmographylist.removeItemDecoration(itemDecoration)
            }

            itemView.filmographylist.adapter =
                MovieCreditsAdapter(
                    layoutManager,
                    movieCasts
                )
        }

        fun bindTVs(
            spanCount: Int,
            tvCasts: List<TVCast>
        ) {
            val layoutManager = (itemView.filmographylist.layoutManager as? GridLayoutManager)
            layoutManager?.spanCount = spanCount

            if (layoutManager?.spanCount == 3) {
                itemView.filmographylist.removeItemDecoration(itemDecoration)
                itemView.filmographylist.addItemDecoration(itemDecoration)
            } else {
                itemView.filmographylist.removeItemDecoration(itemDecoration)
            }

            itemView.filmographylist.adapter =
                TVCreditsAdapter(
                    layoutManager,
                    tvCasts
                )
        }
    }
}
