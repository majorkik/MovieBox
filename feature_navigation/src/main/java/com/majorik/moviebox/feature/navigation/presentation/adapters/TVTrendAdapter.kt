package com.majorik.moviebox.feature.navigation.presentation.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.library.base.constants.BaseIntentKeys
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.tv.TV
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.startDetailsActivityWithId
import com.majorik.library.base.extensions.toDate
import com.majorik.library.base.utils.GenresStorageObject
import com.majorik.library.base.utils.PACKAGE_NAME
import com.majorik.moviebox.feature.navigation.databinding.ItemTrendCardWithTitleBinding
import com.majorik.moviebox.feature.navigation.databinding.ItemTrendLastItemCardBinding
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
        return if (tvs.size > 0) {
            tvs.size + 1
        } else {
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

                holder.parent.root.setSafeOnClickListener {
                    holder.itemView.context.startDetailsActivityWithId(
                        "$PACKAGE_NAME.feature.details.presentation.tvDetails.TVDetailsActivity",
                        Intent().apply {
                            putExtra(BaseIntentKeys.ITEM_ID, tvs[position].id)
                        }
                    )
                }
            }

            else -> {
                holder.itemView.setSafeOnClickListener {
                }
            }
        }
    }

    class TVTrendViewHolder(val parent: ItemTrendCardWithTitleBinding) :
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

    class TVTrendLastItemVH(parent: ItemTrendLastItemCardBinding) :
        RecyclerView.ViewHolder(parent.root)
}
