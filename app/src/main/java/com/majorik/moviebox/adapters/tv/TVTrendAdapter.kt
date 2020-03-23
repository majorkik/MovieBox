package com.majorik.moviebox.adapters.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.tmdbModels.tv.TV
import com.majorik.moviebox.databinding.ItemTrendCardWithTitleBinding
import com.majorik.moviebox.databinding.ItemTrendLastItemCardBinding
import com.majorik.moviebox.extensions.setSafeOnClickListener
import com.majorik.moviebox.extensions.startDetailsActivityWithId
import com.majorik.moviebox.extensions.toDate
import com.majorik.moviebox.ui.tvDetails.TVDetailsActivity
import com.majorik.moviebox.utils.GenresStorageObject
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

    internal fun addItems(items: List<TV>) {
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
                        TVDetailsActivity::class.java
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