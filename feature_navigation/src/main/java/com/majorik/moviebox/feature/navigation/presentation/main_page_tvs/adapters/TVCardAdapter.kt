package com.majorik.moviebox.feature.navigation.presentation.main_page_tvs.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.moviebox.feature.navigation.databinding.ItemBigImageWithCornersBinding
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.tv.TV
import com.majorik.moviebox.feature.navigation.domain.utils.getTVDiffUtils
import com.majorik.moviebox.feature.navigation.presentation.main_page_tvs.adapters.TVCardAdapter.TVCardViewHolder

class TVCardAdapter(private val clickAction: (id: Int) -> Unit) :
    PagingDataAdapter<TV, TVCardViewHolder>(getTVDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVCardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBigImageWithCornersBinding.inflate(layoutInflater, parent, false)

        return TVCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TVCardViewHolder, position: Int) {
        getItem(position)?.let { tv ->
            holder.bindTo(tv)

            holder.viewBinding.sliderLayout.setSafeOnClickListener {
                clickAction(tv.id)
            }
        }
    }

    class TVCardViewHolder(val viewBinding: ItemBigImageWithCornersBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bindTo(tv: TV) {
            viewBinding.placeholderText.text = tv.name

            viewBinding.sliderImage.displayImageWithCenterCrop(
                UrlConstants.TMDB_BACKDROP_SIZE_1280 + tv.backdropPath
            )
        }
    }
}
