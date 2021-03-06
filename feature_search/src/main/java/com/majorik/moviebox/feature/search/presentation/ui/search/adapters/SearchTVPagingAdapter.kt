package com.majorik.moviebox.feature.search.presentation.ui.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.moviebox.feature.search.R
import com.majorik.moviebox.feature.search.databinding.ItemCardWithDetailsBinding
import com.majorik.moviebox.feature.search.databinding.ItemSearchMediumPosterCardBinding
import com.majorik.moviebox.feature.search.domain.extensions.getTVDiffUtils
import com.majorik.moviebox.feature.search.domain.tmdbModels.tv.TV
import com.majorik.moviebox.feature.search.presentation.viewholders.SearchTVDetailedVH
import com.majorik.moviebox.feature.search.presentation.viewholders.SearchTVSmallVH

internal class SearchTVPagingAdapter(private val actionClick: (id: Int) -> Unit) :
    PagingDataAdapter<TV, ViewHolder>(getTVDiffUtils()) {

    private var isGrid: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_card_with_details -> SearchTVDetailedVH(
                ItemCardWithDetailsBinding.inflate(layoutInflater, parent, false)
            )

            R.layout.item_search_medium_poster_card -> SearchTVSmallVH(
                ItemSearchMediumPosterCardBinding.inflate(layoutInflater, parent, false)
            )

            else -> throw IllegalArgumentException("Неизвестный тип view: $viewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) ?: return

        when (holder) {
            is SearchTVDetailedVH -> {
                holder.bindTo(item)

                holder.viewBinding.root.setSafeOnClickListener {
                    actionClick(item.id)
                }
            }

            is SearchTVSmallVH -> {
                holder.bindTo(item)

                holder.viewBinding.collectionCard.setSafeOnClickListener {
                    actionClick(item.id)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isGrid) {
            R.layout.item_search_medium_poster_card
        } else {
            R.layout.item_card_with_details
        }
    }

    fun setViewType(isGrid: Boolean) {
        this.isGrid = isGrid
    }
}
