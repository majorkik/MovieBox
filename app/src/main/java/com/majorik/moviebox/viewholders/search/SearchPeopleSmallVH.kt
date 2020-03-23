package com.majorik.moviebox.viewholders.search

import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.person.Person
import com.majorik.moviebox.R
import com.majorik.moviebox.databinding.ItemPersonSmallCardBinding
import kotlinx.android.synthetic.main.item_person_small_card.view.*

class SearchPeopleSmallVH(val parent: ItemPersonSmallCardBinding) : RecyclerView.ViewHolder(parent.root) {
    fun bindTo(item: Person?) {
        item?.let {
            parent.pImage.load(UrlConstants.TMDB_PROFILE_SIZE_185 + it.profilePath) {
                error(R.drawable.placholder_profile_image)
                placeholder(R.drawable.placholder_profile_image)
            }
        }
    }
}
