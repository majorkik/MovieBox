package com.majorik.moviebox.feature.navigation.presentation.main_page_profile.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.majorik.library.base.constants.BaseIntentKeys
import com.majorik.library.base.constants.ScreenLinks
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.startActivityWithAnim
import com.majorik.moviebox.feature.navigation.databinding.ItemProfileCardBinding
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.tv.TV

class ProfileTVsAdapter : RecyclerView.Adapter<ProfileTVsAdapter.ProfileTVViewHolder>() {

    private var items: List<TV> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileTVViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemProfileCardBinding.inflate(layoutInflater, parent, false)
        return ProfileTVViewHolder(
            binding
        )
    }

    override fun getItemCount() = items.count()

    override fun onBindViewHolder(holder: ProfileTVViewHolder, position: Int) {
        holder.bindTo(items[position])

        holder.itemView.setSafeOnClickListener {
            holder.itemView.context.startActivityWithAnim(
                ScreenLinks.tvDetails,
                Intent().apply {
                    putExtra(BaseIntentKeys.ITEM_ID, items[position].id)
                }
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun addItems(tvs: List<TV>) {
        items = tvs
        notifyItemRangeInserted(0, tvs.count())
    }

    class ProfileTVViewHolder(private val view: ItemProfileCardBinding) : RecyclerView.ViewHolder(view.root) {
        fun bindTo(tv: TV) {
            view.imageView.load(UrlConstants.TMDB_POSTER_SIZE_185 + tv.posterPath)
        }
    }
}
