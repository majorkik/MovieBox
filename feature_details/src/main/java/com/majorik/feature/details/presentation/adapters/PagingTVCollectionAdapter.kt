package com.majorik.feature.details.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.majorik.feature.details.domain.NetworkState
import com.majorik.moviebox.R
import com.majorik.moviebox.databinding.ItemMediumPosterCardBinding
import com.majorik.moviebox.databinding.ItemNetworkStateBinding
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.startDetailsActivityWithId
import com.majorik.feature.details.presentation.tvDetails.TVDetailsActivity
import com.majorik.feature.details.domain.tmdbModels.tv.TV
import com.majorik.feature.details.presentation.NetworkStateViewHolder
import com.majorik.feature.details.presentation.TVPagedItemVH

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
            R.layout.item_medium_poster_card -> TVPagedItemVH(
                ItemMediumPosterCardBinding.inflate(layoutInfalter, parent, false)
            )
            R.layout.item_network_state -> NetworkStateViewHolder(
                ItemNetworkStateBinding.inflate(layoutInfalter, parent, false)
            )
            else -> throw IllegalArgumentException("Неизвестный тип view: $viewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_medium_poster_card -> {
                (holder as TVPagedItemVH).bindTo(getItem(position))

                holder.itemView.setSafeOnClickListener {
                    getItem(position)?.let { tv ->
                        holder.itemView.context.startDetailsActivityWithId(
                            tv.id,
                            TVDetailsActivity::class.java
                        )
                    }
                }
            }
            R.layout.item_network_state -> {
                (holder as NetworkStateViewHolder).bindTo(
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
            R.layout.item_medium_poster_card
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
