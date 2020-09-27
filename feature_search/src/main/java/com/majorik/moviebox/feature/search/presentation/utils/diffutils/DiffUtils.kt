package com.majorik.moviebox.feature.search.presentation.utils.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.majorik.moviebox.feature.search.domain.models.discover.BaseDiscoverDomainModel

fun getDiscoverDiffUtils() = object : DiffUtil.ItemCallback<BaseDiscoverDomainModel>() {
    override fun areItemsTheSame(
        oldItem: BaseDiscoverDomainModel,
        newItem: BaseDiscoverDomainModel
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: BaseDiscoverDomainModel,
        newItem: BaseDiscoverDomainModel
    ): Boolean = oldItem == newItem
}
