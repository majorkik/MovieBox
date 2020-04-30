package com.majorik.moviebox.feature.search.presentation.adapters.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.majorik.moviebox.feature.search.domain.NetworkState
import com.majorik.moviebox.feature.search.domain.tmdbModels.tv.TV
import com.majorik.moviebox.feature.search.presentation.viewholders.NetworkStateViewHolder
import com.majorik.moviebox.feature.search.presentation.viewholders.SearchTVDetailedVH
import com.majorik.moviebox.feature.search.presentation.viewholders.SearchTVSmallVH
import com.majorik.moviebox.R
import com.majorik.moviebox.databinding.ItemCardWithDetailsBinding
import com.majorik.moviebox.databinding.ItemMediumPosterCardBinding
import com.majorik.moviebox.databinding.ItemNetworkStateBinding
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.startDetailsActivityWithId
import com.majorik.library.base.utils.PACKAGE_NAME
import com.majorik.moviebox.feature.search.presentation.adapters.PaginationOnClickListener
import kotlinx.android.synthetic.main.item_search_medium_poster_card.view.*

internal class SearchTVAdapter(
    private val callback: PaginationOnClickListener
) : PagedListAdapter<TV, ViewHolder>(diffCallback) {

    private var networkState: NetworkState? = null
    private var isGrid: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_card_with_details -> SearchTVDetailedVH(
                ItemCardWithDetailsBinding.inflate(layoutInflater, parent, false)
            )

            R.layout.item_medium_poster_card -> SearchTVSmallVH(
                ItemMediumPosterCardBinding.inflate(layoutInflater, parent, false)
            )

            R.layout.item_network_state -> NetworkStateViewHolder(
                ItemNetworkStateBinding.inflate(layoutInflater, parent, false)
            )

            else -> throw IllegalArgumentException("Неизвестный тип view: $viewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is SearchTVDetailedVH -> {
                holder.bindTo(getItem(position))

                holder.parent.root.setSafeOnClickListener {
                    getItem(position)?.let { item ->
                        holder.itemView.context.startDetailsActivityWithId(
                            item.id,
                            "$PACKAGE_NAME.feature.details.presentation.tvDetails.TVDetailsActivity"
                        )
                    }
                }
            }

            is SearchTVSmallVH -> {
                holder.bindTo(getItem(position))

                holder.parent.collectionCard.setSafeOnClickListener {
                    getItem(position)?.let { item ->
                        holder.itemView.context.startDetailsActivityWithId(
                            item.id,
                            "$PACKAGE_NAME.feature.details.presentation.tvDetails.TVDetailsActivity"
                        )
                    }
                }
            }

            is NetworkStateViewHolder -> {
                holder.bindTo(
                    networkState,
                    callback
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_state
        } else {
            if (isGrid) {
                R.layout.item_medium_poster_card
            } else {
                R.layout.item_card_with_details
            }
        }
    }

    override fun getItemCount(): Int {
        this.callback.whenListIsUpdated(super.getItemCount(), this.networkState)
        return super.getItemCount()
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.SUCCESS

    fun setViewType(isGrid: Boolean) {
        this.isGrid = isGrid
    }

    fun updateNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<TV>() {
            override fun areItemsTheSame(
                oldItem: TV,
                newItem: TV
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: TV,
                newItem: TV
            ): Boolean = oldItem == newItem
        }
    }
}
