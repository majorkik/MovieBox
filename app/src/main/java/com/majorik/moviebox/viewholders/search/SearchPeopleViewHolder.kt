package com.majorik.moviebox.viewholders.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.person.Person
import com.majorik.moviebox.R
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import kotlinx.android.synthetic.main.item_card_with_details.view.*
import kotlinx.android.synthetic.main.item_person_detailed.view.*

class SearchPeopleViewHolder(val parent: View) : RecyclerView.ViewHolder(parent) {
    fun bindTo(item: Person?) {
        item?.let {
            itemView.p_name.text = it.name
            itemView.p_image.displayImageWithCenterCrop(
                UrlConstants.TMDB_PROFILE_SIZE_185 + it.profilePath,
                R.drawable.placholder_profile_image
            )
            itemView.p_know_for_department.text = it.knowForDepartment
        }
    }
}
