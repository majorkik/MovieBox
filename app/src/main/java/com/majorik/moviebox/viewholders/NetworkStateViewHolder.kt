package com.majorik.moviebox.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.NetworkState
import com.majorik.moviebox.adapters.PagingMovieCollectionAdapter
import com.majorik.moviebox.adapters.PagingTVCollectionAdapter
import com.majorik.moviebox.adapters.SearchAdapter
import kotlinx.android.synthetic.main.item_network_state.view.*

class NetworkStateViewHolder(containerView: View) :
    RecyclerView.ViewHolder(containerView) {
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
