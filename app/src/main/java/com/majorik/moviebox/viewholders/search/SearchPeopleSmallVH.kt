package com.majorik.moviebox.viewholders.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.person.Person
import com.majorik.moviebox.R
import kotlinx.android.synthetic.main.item_person_small_card.view.*

class SearchPeopleSmallVH(val parent: View) : RecyclerView.ViewHolder(parent) {
    fun bindTo(item: Person?) {
        item?.let {
            itemView.p_image.load(UrlConstants.TMDB_PROFILE_SIZE_185 + it.profilePath) {
                error(R.drawable.placholder_profile_image)
                placeholder(R.drawable.placholder_profile_image)
            }
        }
    }
}
