package com.majorik.moviebox.feature.collections.presentation.adapters.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.collections.domain.tmdbModels.tv.TV
import com.majorik.library.base.constants.UrlConstants
import com.majorik.moviebox.feature.collections.presentation.adapters.tv.TVCollectionAdapter.*
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.startDetailsActivityWithId
import com.majorik.library.base.utils.PACKAGE_NAME
import com.majorik.moviebox.feature.collections.databinding.ItemSmallPosterCardBinding

class TVCollectionAdapter() : RecyclerView.Adapter<CollectionViewHolder>() {

    private val tvs: MutableList<TV> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            ItemSmallPosterCardBinding.inflate(layoutInflater, parent, false)

        return CollectionViewHolder(binding)
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

    override fun getItemCount() = tvs.size

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        holder.bindTo(tvs[position])
    }

    class CollectionViewHolder(private val parent: ItemSmallPosterCardBinding) :
        RecyclerView.ViewHolder(parent.root) {
        fun bindTo(tv: TV) {
            parent.placeholderText.text = tv.name

            parent.collectionImage.displayImageWithCenterCrop(UrlConstants.TMDB_POSTER_SIZE_185 + tv.posterPath)

            bindClickListener(tv)
        }

        private fun bindClickListener(tv: TV) {
            parent.collectionCard.setSafeOnClickListener {
                itemView.context.startDetailsActivityWithId(
                    tv.id,
                   "$PACKAGE_NAME.feature.details.presentation.tvDetails.TVDetailsActivity"
                )
            }
        }
    }
}
