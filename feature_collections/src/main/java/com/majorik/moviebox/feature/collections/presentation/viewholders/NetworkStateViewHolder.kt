package com.majorik.moviebox.feature.collections.presentation.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.collections.databinding.ItemCollectionNetworkStateBinding
import com.majorik.moviebox.feature.collections.domain.NetworkState
import com.majorik.moviebox.feature.collections.presentation.adapters.PagingMovieCollectionAdapter
import com.majorik.moviebox.feature.collections.presentation.adapters.PagingTVCollectionAdapter
import kotlinx.android.synthetic.main.item_collection_network_state.view.*

class NetworkStateViewHolder(containerView: ItemCollectionNetworkStateBinding) :
    RecyclerView.ViewHolder(containerView.root) {
    fun bindTo(
        networkState: NetworkState?,
        callback: PagingMovieCollectionAdapter.OnClickListener
    ) {
        hideViews()
        setVisibleRightViews(networkState)
        itemView.item_paging_network_state_button?.setOnClickListener { callback.onClickRetry() }
    }

    fun bindTo(networkState: NetworkState?, callback: PagingTVCollectionAdapter.OnClickListener) {
        hideViews()
        setVisibleRightViews(networkState)
        itemView.item_paging_network_state_button.setOnClickListener { callback.onClickRetry() }
    }

    private fun hideViews() {
        itemView.item_paging_network_state_button?.visibility = View.GONE
        itemView.item_paging_network_state_progress_bar?.visibility = View.GONE
        itemView.item_paging_network_state_title?.visibility = View.GONE
    }

    private fun setVisibleRightViews(networkState: NetworkState?) {
        when (networkState) {
            NetworkState.FAILED -> {
                itemView.item_paging_network_state_button?.visibility = View.VISIBLE
                itemView.item_paging_network_state_title?.visibility = View.VISIBLE
            }
            NetworkState.RUNNING -> {
                itemView.item_paging_network_state_progress_bar?.visibility = View.VISIBLE
            }
            NetworkState.SUCCESS -> {
            }
            null -> {
            }
        }
    }
}
