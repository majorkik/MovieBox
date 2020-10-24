package com.majorik.moviebox.feature.navigation.presentation.main_page_tvs.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.moviebox.feature.navigation.databinding.ItemSmallPosterCardBinding
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.tv.TV
import com.majorik.moviebox.feature.navigation.domain.utils.getTVDiffUtils

class TVCollectionAdapter(private val clickAction: (id: Int) -> Unit) :
    PagingDataAdapter<TV, TVCollectionAdapter.CollectionViewHolder>(getTVDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            ItemSmallPosterCardBinding.inflate(layoutInflater, parent, false)

        return CollectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        getItem(position)?.let { tv ->
            holder.bindTo(tv)

            holder.viewBinding.collectionCard.setSafeOnClickListener {
                clickAction(tv.id)
            }
        }
    }

    class CollectionViewHolder(val viewBinding: ItemSmallPosterCardBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bindTo(tv: TV) {
            viewBinding.placeholderText.text = tv.name

            viewBinding.collectionImage.displayImageWithCenterCrop(UrlConstants.TMDB_POSTER_SIZE_185 + tv.posterPath)
        }
    }
}
