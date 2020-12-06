package com.majorik.moviebox.feature.details.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.details.domain.tmdbModels.cast.MovieCast
import com.majorik.moviebox.feature.details.domain.tmdbModels.cast.TVCast
import com.majorik.moviebox.feature.details.presentation.adapters.PersonFilmographyPagerAdapter.PageViewHolder
import com.majorik.moviebox.feature.details.presentation.adapters.movie.MovieCreditsAdapter
import com.majorik.moviebox.feature.details.presentation.adapters.tv.TVCreditsAdapter
import com.majorik.library.base.extensions.px
import com.majorik.library.base.utils.SpacingDecoration
import com.majorik.moviebox.feature.details.databinding.ItemFilmographyDetailsBinding

class PersonFilmographyPagerAdapter(
    private val actionMovieClick: (id: Int) -> Unit,
    private val actionTVClick: (id: Int) -> Unit,
    private val movieCasts: List<MovieCast>,
    private val tvCast: List<TVCast>
) : RecyclerView.Adapter<PageViewHolder>() {
    private var spanCount = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemFilmographyDetailsBinding.inflate(layoutInflater, parent, false)

        return PageViewHolder(viewBinding)
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
        spanCount = if (isGrid) {
            3
        } else {
            1
        }

        notifyDataSetChanged()
    }

    inner class PageViewHolder(val viewBinding: ItemFilmographyDetailsBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        private val itemDecoration = SpacingDecoration(16.px(), 16.px(), true)

        fun bindMovies(
            spanCount: Int,
            movieCasts: List<MovieCast>
        ) {
            val layoutManager = (viewBinding.filmographylist.layoutManager as? GridLayoutManager)
            layoutManager?.spanCount = spanCount

            if (layoutManager?.spanCount == 3) {
                viewBinding.filmographylist.removeItemDecoration(itemDecoration)
                viewBinding.filmographylist.addItemDecoration(itemDecoration)
            } else {
                viewBinding.filmographylist.removeItemDecoration(itemDecoration)
            }

            viewBinding.filmographylist.adapter = MovieCreditsAdapter(
                { id ->
                    actionMovieClick(id)
                },
                layoutManager, movieCasts
            )
        }

        fun bindTVs(
            spanCount: Int,
            tvCasts: List<TVCast>
        ) {
            val layoutManager = (viewBinding.filmographylist.layoutManager as? GridLayoutManager)
            layoutManager?.spanCount = spanCount

            if (layoutManager?.spanCount == 3) {
                viewBinding.filmographylist.removeItemDecoration(itemDecoration)
                viewBinding.filmographylist.addItemDecoration(itemDecoration)
            } else {
                viewBinding.filmographylist.removeItemDecoration(itemDecoration)
            }

            viewBinding.filmographylist.adapter = TVCreditsAdapter(
                { id ->
                    actionTVClick(id)
                },
                layoutManager, tvCasts
            )
        }
    }
}
