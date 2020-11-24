package com.majorik.moviebox.feature.search.presentation.ui.discover.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.library.base.extensions.setIndicatorColor
import com.majorik.library.base.utils.GenresStorageObject
import com.majorik.moviebox.feature.search.R
import com.majorik.moviebox.feature.search.databinding.ItemCardWithDetailsBinding
import com.majorik.moviebox.feature.search.databinding.ItemSearchMediumPosterCardBinding
import com.majorik.moviebox.feature.search.domain.extensions.getDiscoverDiffUtils
import com.majorik.moviebox.feature.search.domain.models.discover.BaseDiscoverDomainModel

class DiscoverAdapter(private val changeItemTypeAction: (isGrid: Boolean) -> Unit) :
    PagingDataAdapter<BaseDiscoverDomainModel, RecyclerView.ViewHolder>(getDiscoverDiffUtils()) {

    // Если нужно, чтобы элементы отображались в виде карточек 3х3, иначе будет отображаться в виде длинных карточек
    // с дополнительной информацией
    private var isGrid: Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_search_medium_poster_card -> {
                val view = ItemSearchMediumPosterCardBinding.inflate(layoutInflater, parent, false)
                DiscoverCardViewHolder(view)
            }

            R.layout.item_card_with_details -> {
                val view = ItemCardWithDetailsBinding.inflate(layoutInflater, parent, false)
                DiscoverDetailsCardViewHolder(view)
            }

            else -> throw IllegalArgumentException("Неизвестный тип view: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DiscoverCardViewHolder -> {
                getItem(position)?.let { holder.bindTo(it) }
            }

            is DiscoverDetailsCardViewHolder -> {
                getItem(position)?.let { holder.bindTo(it) }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isGrid) {
            R.layout.item_search_medium_poster_card
        } else {
            R.layout.item_card_with_details
        }
    }

    /**
     * Метод [setViewType] для смены типа ячеек
     */
    fun setViewType(isGrid: Boolean) {
        this.isGrid = isGrid

        changeItemTypeAction(isGrid)
    }

    class DiscoverCardViewHolder(private val viewBinding: ItemSearchMediumPosterCardBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bindTo(item: BaseDiscoverDomainModel) {
            viewBinding.collectionImage.displayImageWithCenterCrop(UrlConstants.TMDB_POSTER_SIZE_185 + item.posterImage)
            viewBinding.title.text = item.name
        }
    }

    class DiscoverDetailsCardViewHolder(private val viewBinding: ItemCardWithDetailsBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bindTo(item: BaseDiscoverDomainModel) {
            viewBinding.cardImage.displayImageWithCenterCrop(UrlConstants.TMDB_BACKDROP_SIZE_1280 + item.backdropImage)
            viewBinding.cardTitle.text = item.name

            item.releaseDate?.let {
                viewBinding.cardReleaseDate.text = it.yearInt.toString()
            }

            viewBinding.cardVoteAverage.text = item.voteAverage.toString()

            viewBinding.cardGenres.text = item.genres?.map { GenresStorageObject.movieGenres.get(it) }
                ?.joinToString(" - ") { (it ?: "").capitalize() }

            viewBinding.voteAverageIndicator.setIndicatorColor(item.voteAverage)
        }
    }
}
