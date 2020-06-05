package com.majorik.moviebox.feature.navigation.presentation.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.majorik.library.base.constants.BaseIntentKeys
import com.majorik.library.base.constants.ScreenLinks
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.startActivityWithAnim
import com.majorik.moviebox.feature.navigation.databinding.ItemBigImageWithCornersBinding
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.tv.TV
import com.majorik.moviebox.feature.navigation.presentation.adapters.TVCardAdapter.TVCardViewHolder

class TVCardAdapter(private val activity: FragmentActivity) :
    RecyclerView.Adapter<TVCardViewHolder>() {

    private var tvs: List<TV> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVCardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBigImageWithCornersBinding.inflate(layoutInflater, parent, false)

        return TVCardViewHolder(binding)
    }

    override fun getItemCount() = tvs.size

    override fun onBindViewHolder(holder: TVCardViewHolder, position: Int) {
        holder.bindTo(tvs[position])

        holder.parent.sliderLayout.setSafeOnClickListener {
            holder.itemView.context.startActivityWithAnim(
                ScreenLinks.tvDetails,
                Intent().apply {
                    putExtra(BaseIntentKeys.ITEM_ID, tvs[position].id)
                }
            )
        }
    }

    fun addItems(items: List<TV>) {
        val startPosition = tvs.size
        tvs = items
        notifyItemRangeInserted(startPosition, items.size)
    }

    override fun getItemId(position: Int): Long {
        return tvs[position].id.toLong()
    }

    class TVCardViewHolder(val parent: ItemBigImageWithCornersBinding) :
        RecyclerView.ViewHolder(parent.root) {
        fun bindTo(tv: TV) {
            parent.placeholderText.text = tv.name

            parent.sliderImage.displayImageWithCenterCrop(
                UrlConstants.TMDB_BACKDROP_SIZE_1280 + tv.backdropPath
            )
        }
    }
}
