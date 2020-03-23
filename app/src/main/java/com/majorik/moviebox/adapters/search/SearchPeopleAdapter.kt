package com.majorik.moviebox.adapters.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.majorik.domain.NetworkState
import com.majorik.domain.NetworkState.SUCCESS
import com.majorik.domain.tmdbModels.person.Person
import com.majorik.moviebox.R
import com.majorik.moviebox.databinding.ItemNetworkStateBinding
import com.majorik.moviebox.databinding.ItemPersonDetailedBinding
import com.majorik.moviebox.databinding.ItemPersonSmallCardBinding
import com.majorik.moviebox.extensions.setSafeOnClickListener
import com.majorik.moviebox.extensions.startDetailsActivityWithId
import com.majorik.moviebox.ui.person_details.PersonDetailsActivity
import com.majorik.moviebox.viewholders.NetworkStateViewHolder
import com.majorik.moviebox.viewholders.search.SearchPeopleSmallVH
import com.majorik.moviebox.viewholders.search.SearchPeopleViewHolder

class SearchPeopleAdapter(
    private val callback: OnClickListener
) : PagedListAdapter<Person, ViewHolder>(diffCallback) {

    private var networkState: NetworkState? = null
    private var isGrid: Boolean = false

    interface OnClickListener {
        fun onClickRetry()
        fun whenListIsUpdated(size: Int, networkState: NetworkState?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_person_detailed -> SearchPeopleViewHolder(
                ItemPersonDetailedBinding.inflate(layoutInflater, parent, false)
            )

            R.layout.item_person_small_card -> SearchPeopleSmallVH(
                ItemPersonSmallCardBinding.inflate(layoutInflater, parent, false)
            )

            R.layout.item_network_state -> NetworkStateViewHolder(
                ItemNetworkStateBinding.inflate(layoutInflater, parent, false)
            )

            else -> throw IllegalArgumentException("Неизвестный тип view: $viewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is SearchPeopleViewHolder -> {
                holder.bindTo(getItem(position))

                holder.itemView.setSafeOnClickListener {
                    getItem(position)?.let { item ->
                        holder.itemView.context.startDetailsActivityWithId(
                            item.id,
                            PersonDetailsActivity::class.java
                        )
                    }
                }
            }

            is SearchPeopleSmallVH -> {
                holder.bindTo(getItem(position))

                holder.itemView.setSafeOnClickListener {
                    getItem(position)?.let { item ->
                        holder.itemView.context.startDetailsActivityWithId(
                            item.id,
                            PersonDetailsActivity::class.java
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
            if (isGrid) {
                R.layout.item_person_small_card
            } else {
                R.layout.item_person_detailed
            }
        }
    }

    override fun getItemCount(): Int {
        this.callback.whenListIsUpdated(super.getItemCount(), this.networkState)
        return super.getItemCount()
    }

    private fun hasExtraRow() = networkState != null && networkState != SUCCESS

    fun setViewType(isGrid: Boolean) {
        this.isGrid = isGrid
    }

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
        private val diffCallback = object : DiffUtil.ItemCallback<Person>() {
            override fun areItemsTheSame(
                oldItem: Person,
                newItem: Person
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: Person,
                newItem: Person
            ): Boolean = oldItem == newItem
        }
    }
}
