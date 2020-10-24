package com.majorik.moviebox.feature.navigation.presentation.main_page_tvs.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.toDate
import com.majorik.library.base.utils.GenresStorageObject
import com.majorik.moviebox.feature.navigation.databinding.ItemTrendCardWithTitleBinding
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.tv.TV
import com.majorik.moviebox.feature.navigation.domain.utils.getTVDiffUtils
import kotlin.math.roundToInt

class TVTrendAdapter(private val clickAction: (id: Int) -> Unit) :
    PagingDataAdapter<TV, TVTrendAdapter.TVTrendViewHolder>(getTVDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVTrendViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTrendCardWithTitleBinding.inflate(layoutInflater, parent, false)

        return TVTrendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TVTrendViewHolder, position: Int) {
        getItem(position)?.let { tv ->
            holder.bindTo(tv)

            holder.viewBinding.root.setSafeOnClickListener {
                clickAction(tv.id)
            }
        }
    }

    class TVTrendViewHolder(val viewBinding: ItemTrendCardWithTitleBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bindTo(tv: TV) {
            viewBinding.mTrendTitle.text = tv.name

            viewBinding.mTrendYear.text = tv.firstAirDate?.toDate()?.yearInt?.toString() ?: ""
            viewBinding.mTrendGenres.text =
                tv.genreIds.map { GenresStorageObject.movieGenres.get(it) }
                    .take(2)
                    .joinToString(" - ") { it ?: "" }

            viewBinding.mTrendRating.text = "${(tv.voteAverage * 10).roundToInt()}%"
        }
    }
}
