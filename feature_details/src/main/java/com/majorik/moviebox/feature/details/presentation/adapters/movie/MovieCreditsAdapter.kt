package com.majorik.moviebox.feature.details.presentation.adapters.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.*
import com.majorik.moviebox.feature.details.R
import com.majorik.moviebox.feature.details.databinding.ItemMediumPosterCardDetailsBinding
import com.majorik.moviebox.feature.details.databinding.ItemPersonCreditInLineDetailsBinding
import com.majorik.moviebox.feature.details.domain.tmdbModels.cast.MovieCast

class MovieCreditsAdapter(
    private val actionClick: (id: Int) -> Unit,
    private val layoutManager: GridLayoutManager?,
    private val credits: List<MovieCast>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class ListType {
        GRID, LINEAR
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ListType.GRID.ordinal -> {

                val viewBinding = ItemMediumPosterCardDetailsBinding.inflate(layoutInflater, parent, false)

                MovieCreditsViewHolder(viewBinding)
            }

            else -> {
                val viewBinding = ItemPersonCreditInLineDetailsBinding.inflate(layoutInflater, parent, false)

                InlineCreditViewHolder(viewBinding)
            }
        }
    }

    override fun getItemCount() = credits.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieCreditsViewHolder -> {
                holder.bindTo(credits[position])

                holder.viewBinding.collectionCard.setSafeOnClickListener {
                    actionClick(credits[position].id)
                }
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

                holder.viewBinding.inlineCreditLayout.setSafeOnClickListener {
                    actionClick(credits[position].id)
                }
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

    class MovieCreditsViewHolder(val viewBinding: ItemMediumPosterCardDetailsBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bindTo(cast: MovieCast) {
            viewBinding.title.text = cast.title
            viewBinding.collectionImage.displayImageWithCenterCrop(
                UrlConstants.TMDB_POSTER_SIZE_185 + (cast.posterPath ?: ""),
                R.drawable.bg_placeholder_card_colored
            )
        }
    }

    class InlineCreditViewHolder(val viewBinding: ItemPersonCreditInLineDetailsBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bindTo(cast: MovieCast, withoutSpace: Boolean) {

            viewBinding.spacePlaceholder.setVisibilityOption(!withoutSpace)

            viewBinding.knownForDepartment.text = viewBinding.root.context.convertStringForFilmography(
                cast.title,
                viewBinding.root.context.getString(R.string.details_inline_filmography_delimiter),
                cast.character
            )

            viewBinding.pcYear.text = if (!cast.releaseDate.isNullOrBlank()) {
                cast.releaseDate.toDate().year.year.toString()
            } else {
                "????"
            }
        }
    }
}
