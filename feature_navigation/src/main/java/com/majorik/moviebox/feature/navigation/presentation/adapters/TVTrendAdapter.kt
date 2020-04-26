package com.majorik.moviebox.feature.navigation.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.tv.TV
import com.majorik.moviebox.databinding.ItemTrendCardWithTitleBinding
import com.majorik.moviebox.databinding.ItemTrendLastItemCardBinding
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.startDetailsActivityWithId
import com.majorik.library.base.extensions.toDate
import com.majorik.library.base.utils.GenresStorageObject
import kotlin.math.roundToInt

class TVTrendAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class TrendViewType {
        ITEM, LAST_ITEM
    }

    private val tvs: MutableList<TV> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return if (viewType == TrendViewType.ITEM.ordinal) {
            val binding = ItemTrendCardWithTitleBinding.inflate(layoutInflater, parent, false)
            TVTrendViewHolder(binding)
        } else {
            val binding = ItemTrendLastItemCardBinding.inflate(layoutInflater, parent, false)
            TVTrendLastItemVH(binding)
        }

    }

    override fun getItemCount(): Int {
        return if(tvs.size > 0){
            tvs.size + 1
        }else{
            0
        }
    }
    override fun getItemViewType(position: Int): Int {
        return if (position < itemCount - 1) {
            TrendViewType.ITEM.ordinal
        } else {
            TrendViewType.LAST_ITEM.ordinal
        }
    }

    fun addItems(items: List<TV>) {
        items.forEach {
            addItem(it)
        }
    }

    private fun addItem(item: TV) {
        val startedPosition = tvs.size
        tvs.add(item)
        notifyItemInserted(startedPosition)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TVTrendViewHolder -> {
                holder.bindTo(tvs[position])

                holder.itemView.setSafeOnClickListener {
                    holder.itemView.context.startDetailsActivityWithId(
                        tvs[position].id,
                        "com.majorik.moviebox.feature.details.presentation.tvDetails.TVDetailsActivity"
                    )
                }
            }

            else -> {
                holder.itemView.setSafeOnClickListener {

                }
            }
        }
    }

    class TVTrendViewHolder(private val parent: ItemTrendCardWithTitleBinding) :
        RecyclerView.ViewHolder(parent.root) {
        fun bindTo(tv: TV) {
            parent.mTrendTitle.text = tv.name

            parent.mTrendYear.text = tv.firstAirDate?.toDate()?.yearInt?.toString() ?: ""
            parent.mTrendGenres.text =
                tv.genreIds.map { GenresStorageObject.movieGenres.get(it) }
                    .take(2)
                    .joinToString(" - ") { it ?: "" }

            parent.mTrendRating.text = "${(tv.voteAverage * 10).roundToInt()}%"
        }
    }

    class TVTrendLastItemVH(private val parent: ItemTrendLastItemCardBinding) :
        RecyclerView.ViewHolder(parent.root) {
    }
}
