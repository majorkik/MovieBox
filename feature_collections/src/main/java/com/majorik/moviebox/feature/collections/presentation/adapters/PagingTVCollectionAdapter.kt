package com.majorik.moviebox.feature.collections.presentation.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.majorik.library.base.constants.BaseIntentKeys
import com.majorik.moviebox.feature.collections.domain.NetworkState
import com.majorik.moviebox.feature.collections.domain.tmdbModels.tv.TV
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.startDetailsActivityWithId
import com.majorik.library.base.utils.PACKAGE_NAME
import com.majorik.moviebox.feature.collections.R
import com.majorik.moviebox.feature.collections.databinding.ItemCollectionMediumPosterCardBinding
import com.majorik.moviebox.feature.collections.databinding.ItemCollectionNetworkStateBinding
import com.majorik.moviebox.feature.collections.presentation.viewholders.NetworkStateViewHolder
import com.majorik.moviebox.feature.collections.presentation.viewholders.TVPagedItemVH

class PagingTVCollectionAdapter(private val callback: OnClickListener) :
    PagedListAdapter<TV, ViewHolder>(
        diffCallback
    ) {

    private var networkState: NetworkState? = null

    interface OnClickListener {
        fun onClickRetry()
        fun whenListIsUpdated(size: Int, networkState: NetworkState?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInfalter = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_collection_medium_poster_card -> TVPagedItemVH(
                ItemCollectionMediumPosterCardBinding.inflate(layoutInfalter, parent, false)
            )
            R.layout.item_collection_network_state -> NetworkStateViewHolder(
                ItemCollectionNetworkStateBinding.inflate(layoutInfalter, parent, false)
            )
            else -> throw IllegalArgumentException("Неизвестный тип view: $viewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_collection_medium_poster_card -> {
                (holder as TVPagedItemVH).bindTo(getItem(position))

                holder.itemView.setSafeOnClickListener {
                    getItem(position)?.let { tv ->
                        holder.itemView.context.startDetailsActivityWithId(
                            "$PACKAGE_NAME.feature.details.presentation.tvDetails.TVDetailsActivity",
                            Intent().apply {
                                putExtra(BaseIntentKeys.ITEM_ID, tv.id)
                            }
                        )
                    }
                }
            }
            R.layout.item_collection_network_state -> {
                (holder as NetworkStateViewHolder).bindTo(
                    networkState,
                    callback
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_collection_network_state
        } else {
            R.layout.item_collection_medium_poster_card
        }
    }

    override fun getItemCount(): Int {
        this.callback.whenListIsUpdated(super.getItemCount(), this.networkState)
        return super.getItemCount()
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.SUCCESS

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
        private val diffCallback =
            object : DiffUtil.ItemCallback<TV>() {
                override fun areItemsTheSame(
                    oldItem: TV,
                    newItem: TV
                ): Boolean =
                    oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: TV,
                    newItem: TV
                ): Boolean =
                    oldItem == newItem
            }
    }
}
