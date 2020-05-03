package com.majorik.moviebox.feature.collections.presentation.adapters.tv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.collections.domain.tmdbModels.cast.TVCast
import com.majorik.library.base.extensions.*
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.utils.PACKAGE_NAME
import kotlinx.android.synthetic.main.item_collection_medium_poster_card.view.*
import kotlinx.android.synthetic.main.item_collection_person_credit_in_line.view.*
import kotlinx.android.synthetic.main.item_collection_small_poster_card.view.collection_card
import kotlinx.android.synthetic.main.item_collection_small_poster_card.view.collection_image
import com.majorik.moviebox.R as AppResources
import com.majorik.moviebox.feature.collections.R

class TVCreditsAdapter(
    private val layoutManager: GridLayoutManager?,
    private val credits: List<TVCast>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class ListType {
        GRID, LINEAR
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ListType.GRID.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_collection_medium_poster_card, parent, false)

                TVCreditsViewHolder(
                    view
                )
            }

            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_collection_person_credit_in_line, parent, false)

                InlineCreditViewHolder(
                    view
                )
            }
        }
    }

    override fun getItemCount() = credits.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TVCreditsViewHolder -> {
                holder.bindTo(credits[position])
            }
            is InlineCreditViewHolder -> {
                var withoutSpace = true

                if (position + 1 < itemCount) {
                    withoutSpace =
                        compareYears(
                            credits[position].firstAirDate,
                            credits[position + 1].firstAirDate
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

    class TVCreditsViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
        fun bindTo(cast: TVCast) {
            itemView.title.text = cast.name

            itemView.collection_image.displayImageWithCenterCrop(
                UrlConstants.TMDB_POSTER_SIZE_185 + cast.posterPath,
                AppResources.drawable.ic_film_placeholder_colored
            )

            bindClickListener(cast)
        }

        private fun bindClickListener(cast: TVCast) {
            itemView.collection_card.setSafeOnClickListener {
                it.context.apply {
                    val intent = "$PACKAGE_NAME.feature.details.presentation.tvDetails.TVDetailsActivity".loadIntentOrReturnNull()

                    intent?.putExtra("id", cast.id)

                    startActivity(intent)
                }
            }
        }
    }

    class InlineCreditViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bindTo(cast: TVCast, withoutSpace: Boolean) {
            itemView.space_placeholder.setVisibilityOption(!withoutSpace)

            itemView.known_for_department.text = view.context.convertStringForFilmograohy(
                cast.name,
                view.context.getString(R.string.collections_inline_filmography_delimiter),
                cast.character
            )

            itemView.pc_year.text = if (!cast.firstAirDate.isNullOrBlank()) {
                cast.firstAirDate!!.toDate().year.year.toString()
            } else {
                "????"
            }

            bindClickListener(cast)
        }

        private fun bindClickListener(cast: TVCast) {
            itemView.inline_credit_layout.setSafeOnClickListener {
                val intent =
                    "$PACKAGE_NAME.feature.details.presentation.tvDetails.TVDetailsActivity".loadIntentOrReturnNull()

                intent?.putExtra("id", cast.id)

                view.context.startActivity(intent)
            }
        }
    }
}
