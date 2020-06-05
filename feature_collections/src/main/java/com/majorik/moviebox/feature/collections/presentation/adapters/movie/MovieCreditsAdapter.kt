package com.majorik.moviebox.feature.collections.presentation.adapters.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.majorik.library.base.constants.BaseIntentKeys.ITEM_ID
import com.majorik.library.base.constants.ScreenLinks
import com.majorik.moviebox.feature.collections.R
import com.majorik.moviebox.feature.collections.domain.tmdbModels.cast.MovieCast
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.*
import kotlinx.android.synthetic.main.item_collection_medium_poster_card.view.*
import kotlinx.android.synthetic.main.item_collection_person_credit_in_line.view.*

class MovieCreditsAdapter(
    private val layoutManager: GridLayoutManager?,
    private val credits: List<MovieCast>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class ListType {
        GRID, LINEAR
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ListType.GRID.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_collection_medium_poster_card, parent, false)

                MovieCreditsViewHolder(
                    view
                )
            }

            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_collection_person_credit_in_line,
                        parent,
                        false
                    )

                InlineCreditViewHolder(
                    view
                )
            }
        }
    }

    override fun getItemCount() = credits.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieCreditsViewHolder -> {
                holder.bindTo(credits[position])
            }
            is InlineCreditViewHolder -> {
                var withoutSpace = true

                if (position + 1 < itemCount) {
                    withoutSpace =
                        compareYears(
                            credits[position].releaseDate,
                            credits[position + 1].releaseDate
                        )
                }

                holder.bindTo(credits[position], withoutSpace)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (layoutManager?.spanCount == 1) ListType.LINEAR.ordinal
        else ListType.GRID.ordinal
    }

    private fun compareYears(year1: String?, year2: String?): Boolean {
        return year1?.toYear().equals(year2?.toYear())
    }

    class MovieCreditsViewHolder(private val parent: View) : RecyclerView.ViewHolder(parent) {
        fun bindTo(cast: MovieCast) {
            itemView.title.text = cast.title
            itemView.collection_image.displayImageWithCenterCrop(
                UrlConstants.TMDB_POSTER_SIZE_185 + (cast.posterPath ?: ""),
                R.drawable.bg_placeholder_card_colored
            )

            bindClickListener(cast)
        }

        private fun bindClickListener(cast: MovieCast) {
            itemView.collection_card.setSafeOnClickListener {
                it.context.startActivityWithAnim(ScreenLinks.movieDetails, Intent().apply {
                    putExtra(ITEM_ID, cast.id)
                })
            }
        }
    }

    class InlineCreditViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bindTo(cast: MovieCast, withoutSpace: Boolean) {
            itemView.space_placeholder.setVisibilityOption(!withoutSpace)

            itemView.known_for_department.text = view.context.convertStringForFilmography(
                cast.title,
                view.context.getString(R.string.collections_inline_filmography_delimiter),
                cast.character
            )

            itemView.pc_year.text = if (!cast.releaseDate.isNullOrBlank()) {
                cast.releaseDate.toDate().year.year.toString()
            } else {
                "????"
            }

            bindClickListener(cast)
        }

        private fun bindClickListener(cast: MovieCast) {
            itemView.inline_credit_layout.setSafeOnClickListener {
                it.context.startActivityWithAnim(ScreenLinks.movieDetails, Intent().apply {
                    putExtra(ITEM_ID, cast.id)
                })
            }
        }
    }
}
