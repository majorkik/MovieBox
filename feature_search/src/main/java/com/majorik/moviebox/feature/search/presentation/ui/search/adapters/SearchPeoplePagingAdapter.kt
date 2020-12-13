package com.majorik.moviebox.feature.search.presentation.ui.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.moviebox.feature.search.databinding.ItemPersonDetailedBinding
import com.majorik.moviebox.feature.search.databinding.ItemPersonSmallCardBinding
import com.majorik.moviebox.feature.search.domain.NetworkState
import com.majorik.moviebox.feature.search.domain.tmdbModels.person.Person
import com.majorik.moviebox.feature.search.presentation.viewholders.SearchPeopleSmallVH
import com.majorik.moviebox.feature.search.presentation.viewholders.SearchPeopleViewHolder
import com.majorik.moviebox.feature.search.R
import com.majorik.moviebox.feature.search.domain.extensions.getPersonDiffUtils

internal class SearchPeoplePagingAdapter(private val actionClick: (id: Int) -> Unit) :
    PagingDataAdapter<Person, ViewHolder>(
        getPersonDiffUtils()
    ) {

    private var networkState: NetworkState? = null
    private var isGrid: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_person_detailed -> SearchPeopleViewHolder(
                ItemPersonDetailedBinding.inflate(layoutInflater, parent, false)
            )

            R.layout.item_person_small_card -> SearchPeopleSmallVH(
                ItemPersonSmallCardBinding.inflate(layoutInflater, parent, false)
            )

            else -> throw IllegalArgumentException("Неизвестный тип view: $viewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) ?: return

        when (holder) {
            is SearchPeopleViewHolder -> {
                holder.bindTo(item)

                holder.viewBinding.root.setSafeOnClickListener {
                    actionClick(item.id)
                }
            }

            is SearchPeopleSmallVH -> {
                holder.bindTo(item)

                holder.viewBinding.root.setSafeOnClickListener {
                    actionClick(item.id)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isGrid) {
            R.layout.item_person_small_card
        } else {
            R.layout.item_person_detailed
        }
    }

    fun setViewType(isGrid: Boolean) {
        this.isGrid = isGrid
    }
}
