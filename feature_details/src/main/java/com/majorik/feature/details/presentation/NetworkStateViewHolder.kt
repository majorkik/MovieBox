package com.majorik.feature.details.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.majorik.feature.details.domain.NetworkState
import com.majorik.feature.details.presentation.adapters.PagingMovieCollectionAdapter
import com.majorik.feature.details.presentation.adapters.PagingTVCollectionAdapter
import com.majorik.moviebox.adapters.SearchAdapter
import com.majorik.moviebox.databinding.ItemNetworkStateBinding
import kotlinx.android.synthetic.main.item_network_state.view.*

class NetworkStateViewHolder(containerView: ItemNetworkStateBinding) :
    RecyclerView.ViewHolder(containerView.root) {
    fun bindTo(networkState: NetworkState?, callback: SearchAdapter.OnClickListener) {
        hideViews()
        setVisibleRightViews(networkState)
        itemView.item_paging_network_state_button.setOnClickListener { callback.onClickRetry() }
    }

    fun bindTo(
        networkState: NetworkState?,
        callback: PagingMovieCollectionAdapter.OnClickListener
    ) {
        hideViews()
        setVisibleRightViews(networkState)
        itemView.item_paging_network_state_button.setOnClickListener { callback.onClickRetry() }
    }

    fun bindTo(networkState: NetworkState?, callback: PagingTVCollectionAdapter.OnClickListener) {
        hideViews()
        setVisibleRightViews(networkState)
        itemView.item_paging_network_state_button.setOnClickListener { callback.onClickRetry() }
    }

    private fun hideViews() {
        itemView.item_paging_network_state_button.visibility = View.GONE
        itemView.item_paging_network_state_progress_bar.visibility = View.GONE
        itemView.item_paging_network_state_title.visibility = View.GONE
    }

    private fun setVisibleRightViews(networkState: NetworkState?) {
        when (networkState) {
            NetworkState.FAILED -> {
                itemView.item_paging_network_state_button.visibility = View.VISIBLE
                itemView.item_paging_network_state_title.visibility = View.VISIBLE
            }
            NetworkState.RUNNING -> {
                itemView.item_paging_network_state_progress_bar.visibility = View.VISIBLE
            }
            NetworkState.SUCCESS -> {
            }
            null -> {
            }
        }
    }
}
