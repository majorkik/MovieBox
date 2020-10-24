package com.majorik.moviebox.feature.navigation.presentation.main_page_profile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.moviebox.feature.navigation.databinding.ItemProfileCardBinding
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.tv.TV
import com.majorik.moviebox.feature.navigation.domain.utils.getTVDiffUtils

class ProfileTVsAdapter(private val actionClick: (id: Int) -> Unit) :
    PagingDataAdapter<TV, ProfileTVsAdapter.ProfileTVViewHolder>(getTVDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileTVViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProfileCardBinding.inflate(layoutInflater, parent, false)

        return ProfileTVViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileTVViewHolder, position: Int) {
        getItem(position)?.let { tv ->
            holder.bindTo(tv)

            holder.itemView.setSafeOnClickListener {
                actionClick(tv.id)
            }
        }
    }

    class ProfileTVViewHolder(private val view: ItemProfileCardBinding) : RecyclerView.ViewHolder(view.root) {
        fun bindTo(tv: TV) {
            view.imageView.load(UrlConstants.TMDB_POSTER_SIZE_185 + tv.posterPath)
        }
    }
}
