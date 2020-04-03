package com.majorik.feature.search.presentation.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.majorik.feature.search.adapters.search.SearchMovieAdapter
import com.majorik.feature.search.adapters.search.SearchPeopleAdapter
import com.majorik.feature.search.adapters.search.SearchTVAdapter
import com.majorik.feature.search.domain.NetworkState
import com.majorik.moviebox.databinding.ItemNetworkStateBinding

class NetworkStateViewHolder(private val containerView: ItemNetworkStateBinding) :
    RecyclerView.ViewHolder(containerView.root) {


    fun bindTo(networkState: NetworkState?, callback: SearchMovieAdapter.OnClickListener) {
        hideViews()
        setVisibleRightViews(networkState)
        containerView.itemPagingNetworkStateButton.setOnClickListener { callback.onClickRetry() }
    }

    fun bindTo(networkState: NetworkState?, callback: SearchTVAdapter.OnClickListener) {
        hideViews()
        setVisibleRightViews(networkState)
        containerView.itemPagingNetworkStateButton.setOnClickListener { callback.onClickRetry() }
    }

    fun bindTo(networkState: NetworkState?, callback: SearchPeopleAdapter.OnClickListener) {
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
