package com.majorik.moviebox.feature.search.presentation.adapters

import com.majorik.moviebox.feature.search.domain.NetworkState

internal interface PaginationOnClickListener {
    fun onClickRetry()
    fun whenListIsUpdated(size: Int, networkState: NetworkState?)
}
