package com.majorik.moviebox.feature.collections.presentation.tvTabCollections.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.moviebox.feature.collections.databinding.ItemCollectionMediumPosterCardBinding
import com.majorik.moviebox.feature.collections.domain.tmdbModels.tv.TV
import com.majorik.moviebox.feature.collections.domain.utils.getTVDiffUtils
import kotlinx.android.synthetic.main.item_collection_medium_poster_card.view.*

class PagingTVCollectionAdapter(private val actionClick: (id: Int) -> Unit) :
    PagingDataAdapter<TV, PagingTVCollectionAdapter.TVPagedViewHolder>(getTVDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVPagedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemCollectionMediumPosterCardBinding.inflate(layoutInflater, parent, false)

        return TVPagedViewHolder(view)
    }

    override fun onBindViewHolder(holder: TVPagedViewHolder, position: Int) {
        getItem(position)?.let { movie ->
            holder.bindTo(movie)

            holder.itemView.collection_card.setSafeOnClickListener {
                actionClick(movie.id)
            }
        }
    }

    class TVPagedViewHolder(val viewBinding: ItemCollectionMediumPosterCardBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bindTo(tv: TV) {
            viewBinding.title.text = tv.name

            viewBinding.collectionImage.displayImageWithCenterCrop(
                UrlConstants.TMDB_POSTER_SIZE_185 + tv.posterPath
            )
        }
    }
}
