package com.majorik.moviebox.feature.details.presentation.adapters.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.details.domain.tmdbModels.tv.TV
import com.majorik.moviebox.feature.details.presentation.adapters.tv.TVCardAdapter.TVCardViewHolder
import com.majorik.moviebox.databinding.ItemBigImageWithCornersBinding
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.startDetailsActivityWithId
import com.majorik.moviebox.feature.details.presentation.tvDetails.TVDetailsActivity
import com.majorik.library.base.constants.UrlConstants
import kotlinx.android.synthetic.main.item_big_image_with_corners_details.view.*


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

        holder.itemView.slider_layout.setSafeOnClickListener {
            holder.itemView.context.startDetailsActivityWithId(
                tvs[position].id,
                TVDetailsActivity::class.java
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

    class TVCardViewHolder(private val parent: ItemBigImageWithCornersBinding) :
        RecyclerView.ViewHolder(parent.root) {
        fun bindTo(tv: TV) {
            parent.placeholderText.text = tv.name

            parent.sliderImage.displayImageWithCenterCrop(
                UrlConstants.TMDB_BACKDROP_SIZE_1280 + tv.backdropPath
            )
        }
    }
}
