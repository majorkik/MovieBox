package com.majorik.moviebox.feature.search.presentation.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.search.domain.NetworkState
import com.majorik.moviebox.databinding.ItemNetworkStateBinding
import com.majorik.moviebox.feature.search.presentation.adapters.PaginationOnClickListener

internal class NetworkStateViewHolder(private val containerView: ItemNetworkStateBinding) :
    RecyclerView.ViewHolder(containerView.root) {

    fun bindTo(networkState: NetworkState?, callback: PaginationOnClickListener) {
        hideViews()
        setVisibleRightViews(networkState)
        containerView.itemPagingNetworkStateButton.setOnClickListener { callback.onClickRetry() }
    }

    private fun hideViews() {
        containerView.itemPagingNetworkStateButton.visibility = View.GONE
        containerView.itemPagingNetworkStateProgressBar.visibility = View.GONE
        containerView.itemPagingNetworkStateTitle.visibility = View.GONE
    }

    private fun setVisibleRightViews(networkState: NetworkState?) {
        when (networkState) {
            NetworkState.FAILED -> {
                containerView.itemPagingNetworkStateButton.visibility = View.VISIBLE
                containerView.itemPagingNetworkStateTitle.visibility = View.VISIBLE
            }
            NetworkState.RUNNING -> {
                containerView.itemPagingNetworkStateProgressBar.visibility = View.VISIBLE
            }
            NetworkState.SUCCESS -> {
            }
            null -> {
            }
        }
    }
}
