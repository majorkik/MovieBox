package com.majorik.moviebox.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.majorik.domain.NetworkState
import com.majorik.domain.NetworkState.SUCCESS
import com.majorik.domain.tmdbModels.search.MultiSearchResponse.MultiSearchItem
import com.majorik.moviebox.R
import com.majorik.moviebox.databinding.ItemCardWithDetailsBinding
import com.majorik.moviebox.databinding.ItemNetworkStateBinding
import com.majorik.moviebox.extensions.setSafeOnClickListener
import com.majorik.moviebox.extensions.startDetailsActivityWithId
import com.majorik.moviebox.ui.movieDetails.MovieDetailsActivity
import com.majorik.moviebox.ui.person_details.PersonDetailsActivity
import com.majorik.moviebox.ui.tvDetails.TVDetailsActivity
import com.majorik.moviebox.viewholders.NetworkStateViewHolder
import com.majorik.moviebox.viewholders.SearchViewHolder

class SearchAdapter(
    private val callback: OnClickListener
) : PagedListAdapter<MultiSearchItem, ViewHolder>(diffCallback) {

    private var networkState: NetworkState? = null

    interface OnClickListener {
        fun onClickRetry()
        fun whenListIsUpdated(size: Int, networkState: NetworkState?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_card_with_details -> SearchViewHolder(
                ItemCardWithDetailsBinding.inflate(layoutInflater, parent, false)
            )
            R.layout.item_network_state -> NetworkStateViewHolder(
                ItemNetworkStateBinding.inflate(layoutInflater, parent, false)
            )
            else -> throw IllegalArgumentException("Неизвестный тип view: $viewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_card_with_details -> {
                (holder as SearchViewHolder).bindTo(getItem(position))

                holder.itemView.setSafeOnClickListener {
                    getItem(position)?.let { item ->
                        holder.itemView.context.startDetailsActivityWithId(
                            item.id,
                            changeScreen(item.mediaType)
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

    private fun changeScreen(mediaType: String): Class<*> {
        return when (mediaType) {
            "movie" -> {
                MovieDetailsActivity::class.java
            }
            "tv" -> {
                TVDetailsActivity::class.java
            }
            "person" -> {
                PersonDetailsActivity::class.java
            }
            else -> throw IllegalArgumentException("Неизвестный тип обьекта: $mediaType")
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<MultiSearchItem>() {
            override fun areItemsTheSame(
                oldItem: MultiSearchItem,
                newItem: MultiSearchItem
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: MultiSearchItem,
                newItem: MultiSearchItem
            ): Boolean = oldItem == newItem
        }
    }
}
