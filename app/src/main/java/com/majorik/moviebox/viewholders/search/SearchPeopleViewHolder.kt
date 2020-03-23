package com.majorik.moviebox.viewholders.search

import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.person.Person
import com.majorik.moviebox.R
import com.majorik.moviebox.databinding.ItemPersonDetailedBinding
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import kotlinx.android.synthetic.main.item_person_detailed.view.*

class SearchPeopleViewHolder(val parent: ItemPersonDetailedBinding) : RecyclerView.ViewHolder(parent.root) {
    fun bindTo(item: Person?) {
        item?.let {
            parent.pName.text = it.name
            parent.pImage.displayImageWithCenterCrop(
                UrlConstants.TMDB_PROFILE_SIZE_185 + it.profilePath,
                R.drawable.placholder_profile_image
            )
            parent.pKnowForDepartment.text = it.knowForDepartment
        }
    }
}
