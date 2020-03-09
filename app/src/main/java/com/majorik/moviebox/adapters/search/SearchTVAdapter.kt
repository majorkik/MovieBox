package com.majorik.moviebox.adapters.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.majorik.domain.NetworkState
import com.majorik.domain.NetworkState.SUCCESS
import com.majorik.domain.tmdbModels.tv.TV
import com.majorik.moviebox.R
import com.majorik.moviebox.extensions.setSafeOnClickListener
import com.majorik.moviebox.extensions.startDetailsActivityWithId
import com.majorik.moviebox.ui.tvDetails.TVDetailsActivity
import com.majorik.moviebox.viewholders.NetworkStateViewHolder
import com.majorik.moviebox.viewholders.search.SearchTVViewHolder

class SearchTVAdapter(
    private val callback: OnClickListener
) : PagedListAdapter<TV, ViewHolder>(diffCallback) {

    private var networkState: NetworkState? = null

    interface OnClickListener {
        fun onClickRetry()
        fun whenListIsUpdated(size: Int, networkState: NetworkState?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_card_with_details -> SearchTVViewHolder(
                view
            )
            R.layout.item_network_state -> NetworkStateViewHolder(
                view
            )
            else -> throw IllegalArgumentException("Неизвестный тип view: $viewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is SearchTVViewHolder -> {
                holder.bindTo(getItem(position))

                holder.itemView.setSafeOnClickListener {
                    getItem(position)?.let { item ->
                        holder.parent.context.startDetailsActivityWithId(
                            item.id,
                            TVDetailsActivity::class.java
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
            R.layout.item_card_with_details
        }
    }

    override fun getItemCount(): Int {
        this.callback.whenListIsUpdated(super.getItemCount(), this.networkState)
        return super.getItemCount()
    }

    private fun hasExtraRow() = networkState != null && networkState != SUCCESS

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